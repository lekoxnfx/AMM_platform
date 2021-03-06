package com.amm.controller;

import com.amm.entity.OrgUserEntity;
import com.amm.entity.TerminalEntity;
import com.amm.exception.InvalidOperatorException;
import com.amm.service.BaseOrgService;
import com.amm.service.OrgUserService;
import com.amm.service.TerminalService;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by csw on 2016/7/24 18:30.
 * Explain:组织机构接口
 */

@RestController
@RequestMapping("api/terminals")
public class TerminalController extends BaseController{

    @Autowired
    private TerminalService terminalService;

    @Autowired
    private BaseOrgService baseOrgService;

    @Autowired
    private OrgUserService orgUserService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public TerminalEntity create(@RequestBody(required = true) TerminalEntity terminalEntity) {

        Validate.notNull(terminalEntity, "The terminalEntity must not be null, create failure.");
        Validate.notNull(terminalEntity.getTerminalCode(), "The terminalCode must not be null, create failure.");
        Validate.notNull(terminalEntity.getTerminalName(), "The terminalName must not be null, create failure.");

        if(!terminalService.isValidTerminalCode(terminalEntity.getTerminalCode())) {
            throw new InvalidOperatorException("终端号无效，数据库中已存在！");
        }

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = userDetails.getUsername();
        String password = userDetails.getPassword();
        OrgUserEntity currentUser = orgUserService.findOrgUser(userName, password);
        Validate.notNull(currentUser, "The currentUser is null, no user login, create failure.");

        terminalEntity.setOrgId(currentUser.getOrgId());
        terminalEntity.setCreator(currentUser.getUserName());
        terminalEntity.setCreateTime(new Date());

        TerminalEntity created = terminalService.create(terminalEntity);

        return created;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<TerminalEntity> getAllByActive(@RequestParam(required = false, defaultValue = "true") Boolean active,
                                               @RequestParam(required = false, defaultValue = "true") Boolean isBind) {

        List<TerminalEntity> terminalEntityList = terminalService.findAllByActive(active, isBind);

        return terminalEntityList;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public TerminalEntity update(@PathVariable Integer id,
                                @RequestBody TerminalEntity terminal) {

        Validate.notNull(id, "The id of terminal must not be null, update failure.");
        Validate.notNull(terminal, "The terminal object must not be null, update failure.");

        terminal.setId(id);

        TerminalEntity updated = terminalService.update(terminal);

        return updated;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public TerminalEntity findOne(@PathVariable Integer id) {

        Validate.notNull(id, "The id must not be null, find failure.");

        return terminalService.findOne(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public TerminalEntity deleteOne(@PathVariable Integer id) {

        Validate.notNull(id, "The id must not be null, delete failure.");

        return terminalService.delete(id);
    }
}
