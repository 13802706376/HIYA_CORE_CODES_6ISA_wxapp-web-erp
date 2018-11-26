package com.yunnex.ops.erp.modules.sys.service;

import java.util.LinkedList;
import java.util.Queue;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunnex.ops.erp.common.config.Global;
import com.yunnex.ops.erp.common.utils.StringUtils;
import com.yunnex.ops.erp.modules.sys.entity.Area;
import com.yunnex.ops.erp.modules.sys.entity.LdapADHelperDto;
import com.yunnex.ops.erp.modules.sys.entity.Office;

@Service
public class OfficeApiService {

    @Autowired
    private OfficeService officeService;

    @Autowired
    private LdapApiService ldapApiService;

    public static final String OFFICE_UNIQUE_KEY = "entryUUID";
    
    private static Logger logger = LoggerFactory.getLogger(OfficeApiService.class);

    public boolean sync() {
        try {
            Queue<LdapADHelperDto> dtoQueue = new LinkedList<LdapADHelperDto>();
            dtoQueue.add(new LdapADHelperDto("5949bdc3a76e40ad9dc9d226504def0a", "ou=运营中心,ou=User,dc=yunnex,dc=com", "1", "0,1", ""));
            String filter = "(&(objectClass=top)(objectClass=organizationalUnit))";
            String[] attrOrganizationalArray = {OFFICE_UNIQUE_KEY, "ou"};
            SearchControls searchControls = new SearchControls();
            searchControls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
            searchControls.setReturningAttributes(attrOrganizationalArray);
            while (!dtoQueue.isEmpty()) {
                LdapADHelperDto dto = dtoQueue.poll();
                NamingEnumeration<SearchResult> answer = ldapApiService.getLdapContext().search(dto.getSearchBase(), filter.toString(),
                                searchControls);
                while (answer.hasMore()) {
                    SearchResult result = answer.next();
                    NamingEnumeration<? extends Attribute> attrs = result.getAttributes().getAll();
                    LdapADHelperDto newDto = new LdapADHelperDto();
                    while (attrs.hasMore()) {
                        Attribute attr = attrs.next();
                        if ("ou".equals(attr.getID())) {
                            newDto.setSearchBase("ou=" + attr.get() + "," + dto.getSearchBase());
                            newDto.setName(attr.get().toString());
                        } else if (OFFICE_UNIQUE_KEY.equals(attr.getID())) {
                            newDto.setId(attr.get().toString());
                            newDto.setParentId(dto.getId());
                            newDto.setParentIds(dto.getParentIds() + dto.getId() + ",");
                        }
                    }
                    dtoQueue.offer(newDto);
                    saveOffice(newDto);
                }
            }
            return true;
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
        }
        return false;
    }

    private void saveOffice(LdapADHelperDto dto) {
        Office office = officeService.get(dto.getId());
        if (null != office) {
            officeService.updateName(dto.getId(), dto.getName());
        } else {
            office = new Office();
            office.setId(dto.getId());
            office.setName(dto.getName());
            Office parentOffice = officeService.get(dto.getParentId());
            if (null != parentOffice) {
                office.setParent(parentOffice);
                int size = officeService.countByParentId(dto.getParentId());
                office.setCode(office.getParent().getCode() + StringUtils.leftPad(String.valueOf(size > 0 ? size + 1 : 1), 3, "0"));
                office.setGrade(String.valueOf(Integer.valueOf(parentOffice.getGrade()) + 1));
            }
            office.setIsNewRecord(true);
            office.setUseable(Global.YES);
            office.setParentIds(dto.getParentIds());
            office.setType("2");
            office.setArea(new Area("2"));
            officeService.save(office);
        }
    }

}
