package com.yunnex.ops.erp.modules.sys.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yunnex.ops.erp.common.config.Global;
import com.yunnex.ops.erp.modules.sys.entity.LdapADHelperDto;
import com.yunnex.ops.erp.modules.sys.entity.Office;
import com.yunnex.ops.erp.modules.sys.entity.Role;
import com.yunnex.ops.erp.modules.sys.entity.User;

@Service
public class UserApiService {

    @Autowired
    private SystemService systemService;

    @Autowired
    private LdapApiService ldapApiService;
    
    @Value("${default_user_role_id}")
    private String DEFAULT_USER_ROLE_ID;

    @Value("${default_user_password}")
    private String DEFAULT_USER_PASSWORD;

    private static final String USER_UNIQUE_KEY = "employeeNumber";

    public boolean sync() {
        //代码优化，减少获取次数
        LdapContext ldapContext =  ldapApiService.getLdapContext();
        List<LdapADHelperDto> organizationList = getOrganizationList(ldapContext);
        return syncUsers(organizationList,ldapContext);
    }


    private boolean syncUsers(List<LdapADHelperDto> organizationList,LdapContext ldapContext) {
        if (CollectionUtils.isEmpty(organizationList)) {
            return false;
        }
        List<String> allUserIdList = systemService.findAllNormalUserIdList();
        String filter = "(&(objectClass=top)(objectClass=person)(objectClass=organizationalPerson))";
        String[] attrOrganizationalArray = {USER_UNIQUE_KEY, "cn", "displayName", "mail", "uid", "mobile"};
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        searchControls.setReturningAttributes(attrOrganizationalArray);
        try {
            for (LdapADHelperDto dto : organizationList) {
                NamingEnumeration<SearchResult> answer =ldapContext.search(dto.getSearchBase(), filter.toString(),
                                searchControls);
                while (answer.hasMore()) {
                    SearchResult result = answer.next();
                    NamingEnumeration<? extends Attribute> attrs = result.getAttributes().getAll();
                    User user = new User();
                    user.setOffice(new Office(dto.getId()));
                    while (attrs.hasMore()) {
                        Attribute attr = attrs.next();
                        if ("cn".equals(attr.getID())) {
                            user.setLoginName(attr.get().toString());
                        } else if (USER_UNIQUE_KEY.equals(attr.getID())) {
                            user.setId(attr.get().toString());
                        } else if ("displayName".equals(attr.getID())) {
                            user.setName(attr.get().toString());
                        } else if ("mail".equals(attr.getID())) {
                            user.setEmail(attr.get().toString());
                        } else if ("uid".equals(attr.getID())) {
                            user.setNo(attr.get().toString());
                        } else if ("mobile".equals(attr.getID())) {
                            user.setMobile(attr.get().toString());
                        }
                    }
                    insertOrUpdateUser(user);
                    allUserIdList.remove(user.getId());
                }
            }
            if (CollectionUtils.isNotEmpty(allUserIdList)) {
                for (String userId : allUserIdList) {
                    systemService.deleteUser(new User(userId));
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    private void insertOrUpdateUser(User user) {
        User old = systemService.getUser(user.getId());
        if (null == old) {
            List<Role> roleList = new ArrayList<Role>();
            roleList.add(new Role(DEFAULT_USER_ROLE_ID));
            user.setRoleList(roleList);
            user.setCompany(new Office("1"));
            user.setUserType("3");
            user.setLoginFlag(Global.YES);
            user.setRemarks("Ldap导入");
            user.setPassword(SystemService.entryptPassword(DEFAULT_USER_PASSWORD));
            user.setIsNewRecord(true);
            systemService.saveLdapUser(user);
        } else {
        }
    }


    private List<LdapADHelperDto> getOrganizationList(LdapContext ldapContext) {
        try {
            List<LdapADHelperDto> resultList = new ArrayList<LdapADHelperDto>();
            Queue<LdapADHelperDto> dtoQueue = new LinkedList<LdapADHelperDto>();
            dtoQueue.add(new LdapADHelperDto("5949bdc3a76e40ad9dc9d226504def0a", "ou=运营中心,ou=User,dc=yunnex,dc=com", "1", "0,1", "运营中心"));
            String filter = "(&(objectClass=top)(objectClass=organizationalUnit))";
            String[] attrOrganizationalArray = {OfficeApiService.OFFICE_UNIQUE_KEY, "ou"};
            SearchControls searchControls = new SearchControls();
            searchControls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
            searchControls.setReturningAttributes(attrOrganizationalArray);
            while (!dtoQueue.isEmpty()) {
                LdapADHelperDto dto = dtoQueue.poll();
                NamingEnumeration<SearchResult> answer = ldapContext.search(dto.getSearchBase(), filter.toString(),
                                searchControls);
                while (answer.hasMore()) {
                    SearchResult result = answer.next();
                    NamingEnumeration<? extends Attribute> attrs = result.getAttributes().getAll();
                    LdapADHelperDto newDto = new LdapADHelperDto();
                    while (attrs.hasMore()) {
                        Attribute attr = attrs.next();
                        if ("ou".equals(attr.getID())) {
                            newDto.setSearchBase("ou=" + attr.get() + "," + dto.getSearchBase());
                        } else if (OfficeApiService.OFFICE_UNIQUE_KEY.equals(attr.getID())) {
                            newDto.setId(attr.get().toString());
                        }
                    }
                    resultList.add(newDto);
                    dtoQueue.offer(newDto);
                }
            }
           resultList.add(new LdapADHelperDto("5949bdc3a76e40ad9dc9d226504def0a", "ou=运营中心,ou=User,dc=yunnex,dc=com", null,null, null));
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
