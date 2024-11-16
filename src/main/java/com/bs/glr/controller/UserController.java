package com.bs.glr.controller;

import com.alibaba.druid.util.StringUtils;
import com.bs.glr.bean.*;
import com.bs.glr.mapper.BsUserMapper;
import com.bs.glr.mapper.DataTableMapper;
import com.bs.glr.mapper.HealthKnowledgeTableMapper;
import com.bs.glr.mapper.MessageTableMapper;
import com.bs.glr.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@RestController
public class UserController {
    @Resource
    UserService userService;
    @Resource
    BsUserMapper bsUserMapper;
    @Resource
    DataTableMapper dataTableMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Resource
    MessageTableMapper messageTableMapper;
    @Resource
    HealthKnowledgeTableMapper healthKnowledgeTableMapper;

    @RequestMapping("/user/login")
    public ResultBean login(String uname, String pwd) {
        if (StringUtils.isEmpty(uname) || StringUtils.isEmpty(pwd)) {
            return ResultBean.returnError().pushMessage("请将用户名和密码输入完整");
        }
        List<BsUser> userList = userService.getUserByUname(uname);
        if (userList == null || userList.size() == 0) {
            return ResultBean.returnError().pushMessage("用户不存在");
        }
        if (!userList.get(0).getPwd().equals(pwd)) {
            return ResultBean.returnError().pushMessage("用户名或密码不正确");
        }
        String uuid = UUID.randomUUID().toString();
//        redisTemplate.opsForValue().set("user_token:" + uuid, uname);
        userService.loginToken(uuid, userList.get(0).getId());

        return ResultBean.returnOk().pushData("token", uuid);
    }




    //用户查询
    @RequestMapping("/auth/selectUserList")
    public ResultBean selectUserList(Integer page,Integer limit) {
        if(page == null ){
            page = 1;
        }
        if(limit == null){
            limit =10;
        }
        PageInfo<BsUser> pageInfo = userService.selectUserListByPage(page,limit);
        long total = pageInfo.getTotal();
        List<BsUser> list = pageInfo.getList();
        List<BsUser> returnList = new ArrayList<>();
        return ResultBean.returnOk().pushData("total", total).pushData("list",list );
    }


    //用户删除
    @RequestMapping("/auth/deleteUser")
    public ResultBean deleteUser(Integer delUid) {
        userService.deleteUserById(delUid);
        return ResultBean.returnOk().pushMessage("删除成功");
    }

    //修改用户密码
    @RequestMapping("/auth/updatePwdByUid")
    public ResultBean updatePwdByUid(Integer uid,String pwd) {

        BsUser bsUser = userService.selectUserByUid(uid);
        if(bsUser == null){
            return ResultBean.returnError().pushMessage("用户不存在");
        }
        userService.updatePwdByUid(uid,pwd);
        return ResultBean.returnOk().pushMessage("修改成功");
    }


    @RequestMapping("/auth/info")
    public ResultBean changePwd (String token){
        BsUser user = userService.getUserByToken(token);
        if(user == null){
            return ResultBean.returnError("用户不存在");
        }
        String role = "";
        if("0".equals(user.getIdentity())){
            role = "man";
        }else {
            role = "pan";
        }
        if(user!=null){
            return ResultBean.returnOk().pushData("name",user.getUname()).pushData("avatar","https://scwtest20200706.oss-cn-beijing.aliyuncs.com/jd.jpg")
                    .pushData("roles",role);
        }
        return ResultBean.returnError();
    }


    @RequestMapping("/auth/getUserInfoByUid")
    public ResultBean  getUserInfoByUid(Integer userUId){
        BsUser userById = userService.getUserById(userUId);
        return ResultBean.returnOk().pushData("data",userById);
    }

    @RequestMapping("/auth/getUserInfoByToken")
    public ResultBean getUserInfoByToken(Integer uid) {

        BsUser userById = userService.getUserById(uid);
        return ResultBean.returnOk().pushData("data",userById);
    }


    @RequestMapping("/auth/bsUserInsert")
    public ResultBean bsUserInsert (String childGender,String childAge,String childName,String parentPhoneNumber,String parentAge,String parentName,String identity,String pwd,String uname){
        List<BsUser> userByUname = userService.getUserByUname(uname);
        if(userByUname != null && userByUname.size()>0){
            return ResultBean.returnError("该用户名已经存在");
        }
        BsUser bsUser = new BsUser();
        bsUser.setUname(uname);
        bsUser.setPwd(pwd);
        bsUser.setIdentity(identity);
        bsUser.setParentName(parentName);
        bsUser.setParentAge(Integer.parseInt(parentAge));
        bsUser.setParentPhoneNumber(parentPhoneNumber);
        bsUser.setChildName(childName);
        bsUser.setChildAge(Integer.parseInt(childAge));
        bsUser.setChildGender(childGender);
        Integer line = bsUserMapper.insertSelective(bsUser);
        Integer id = bsUser.getId();
        userService.insertToken(id,"-1");
        if(line>0){
            return ResultBean.returnOk();
        }
        return ResultBean.returnError();
    }


    @RequestMapping("/auth/bsUserDeleteById")
    public ResultBean bsUserDeleteById (Integer bid){
        BsUser bsUser = new BsUser();
        bsUser.setId(bid);
        Integer line = bsUserMapper.deleteByPrimaryKey(bid);
        if(line>0){
            return ResultBean.returnOk();
        }
        return ResultBean.returnError();
    }


    @RequestMapping("/auth/bsUserUpdate")
    public ResultBean bsUserUpdate (Integer changeId,String childGender,String childAge,String childName,String parentPhoneNumber,String parentAge,String parentName,String identity,String pwd,String uname){

        BsUser bsUser = new BsUser();
        bsUser.setUname(uname);
        bsUser.setPwd(pwd);
        bsUser.setIdentity(identity);
        bsUser.setParentName(parentName);
        bsUser.setParentAge(Integer.parseInt(parentAge));
        bsUser.setParentPhoneNumber(parentPhoneNumber);
        bsUser.setChildName(childName);
        bsUser.setChildAge(Integer.parseInt(childAge));
        bsUser.setChildGender(childGender);
        bsUser.setId(changeId);
        Integer line = bsUserMapper.updateByPrimaryKeySelective(bsUser);
        if(line>0){
            return ResultBean.returnOk();
        }
        return ResultBean.returnError();
    }


    @RequestMapping("/auth/bsUserSelectList")
    public ResultBean bsUserSelectList (Integer page,Integer size){
        if(page == null){
            page = 1;
        }
        if(size == null){
            size = 10;
        }
        PageHelper.startPage(page,size);
        BsUserExample example = new BsUserExample();
        example.setOrderByClause("id desc");
        example.createCriteria().andIdentityNotEqualTo("0");
        List<BsUser> bsData = bsUserMapper.selectByExample(example);
        PageInfo<BsUser> pageInfo = new PageInfo<>(bsData);
        long total = pageInfo.getTotal();
        List<BsUser> list = pageInfo.getList();
        if(list==null || list.size()<=0){

            return ResultBean.returnError().pushMessage("暂时没有数据");
        }
        return ResultBean.returnOk().pushData("total",total).pushData("data",list);
    }


    @RequestMapping("/auth/bsUserSelectById")
    public ResultBean bsUserSelectById (Integer changeId){
        BsUser bsUser = bsUserMapper.selectByPrimaryKey(changeId);
        return ResultBean.returnOk().pushData("data",bsUser);
    }

    @RequestMapping("/auth/dataTableInsert")
    public ResultBean dataTableInsert (String ctime,String bodyTemperature,String bloodOxygen,String heartRate,String childName,String uname){

        DataTable dataTable = new DataTable();
        dataTable.setUname(uname);
        dataTable.setChildName(childName);
        dataTable.setHeartRate(heartRate);
        dataTable.setBloodOxygen(bloodOxygen);
        dataTable.setBodyTemperature(bodyTemperature);
        dataTable.setCtime(new Date());
        Integer line = dataTableMapper.insertSelective(dataTable);
        if(line>0){
            return ResultBean.returnOk();
        }
        return ResultBean.returnError();
    }


    @RequestMapping("/auth/dataTableDeleteById")
    public ResultBean dataTableDeleteById (Integer bid){
        DataTable dataTable = new DataTable();
        dataTable.setId(bid);
        Integer line = dataTableMapper.deleteByPrimaryKey(bid);
        if(line>0){
            return ResultBean.returnOk();
        }
        return ResultBean.returnError();
    }


    @RequestMapping("/auth/dataTableUpdate")
    public ResultBean dataTableUpdate (Integer changeId,String ctime,String bodyTemperature,String bloodOxygen,String heartRate,String childName,String uname){

        DataTable dataTable = new DataTable();
        dataTable.setUname(uname);
        dataTable.setChildName(childName);
        dataTable.setHeartRate(heartRate);
        dataTable.setBloodOxygen(bloodOxygen);
        dataTable.setBodyTemperature(bodyTemperature);
//        dataTable.setCtime(new D+);
        dataTable.setId(changeId);
        Integer line = dataTableMapper.updateByPrimaryKeySelective(dataTable);
        if(line>0){
            return ResultBean.returnOk();
        }
        return ResultBean.returnError();
    }


    @RequestMapping("/auth/dataTableSelectList")
    public ResultBean dataTableSelectList (Integer page,Integer size,Integer uidc){
        if(page == null){
            page = 1;
        }
        if(size == null){
            size = 10;
        }
        PageHelper.startPage(page,size);
        DataTableExample example = new DataTableExample();
        example.setOrderByClause("id desc");
        BsUser userById = userService.getUserById(uidc);
        if("1".equals(userById.getIdentity())){
            example.createCriteria().andUnameEqualTo(userById.getUname());
        }
        List<DataTable> bsData = dataTableMapper.selectByExample(example);
        PageInfo<DataTable> pageInfo = new PageInfo<>(bsData);
        long total = pageInfo.getTotal();
        List<DataTable> list = pageInfo.getList();
        if(list==null || list.size()<=0){

            return ResultBean.returnError().pushMessage("暂时没有数据");
        }
        return ResultBean.returnOk().pushData("total",total).pushData("data",list);
    }


    @RequestMapping("/auth/dataTableSelectById")
    public ResultBean dataTableSelectById (Integer changeId){
        DataTable dataTable = dataTableMapper.selectByPrimaryKey(changeId);
        return ResultBean.returnOk().pushData("data",dataTable);
    }


    @RequestMapping("/auth/messageTableInsert")
    public ResultBean messageTableInsert (String messageStatus,String messageReply,String messageTime,String messageContent,String realName,String uname,Integer uidc){
        BsUser userById = userService.getUserById(uidc);
        MessageTable messageTable = new MessageTable();
        messageTable.setUname(userById.getUname());
        messageTable.setRealName(userById.getParentName());
        messageTable.setMessageContent(messageContent);
        messageTable.setMessageTime(new Date());
        messageTable.setMessageStatus("正常");
        Integer line = messageTableMapper.insertSelective(messageTable);
        if(line>0){
            return ResultBean.returnOk();
        }
        return ResultBean.returnError();
    }


    @RequestMapping("/auth/messageTableDeleteById")
    public ResultBean messageTableDeleteById (Integer bid){
        MessageTable messageTable = new MessageTable();
        messageTable.setId(bid);
        messageTable.setMessageStatus("封禁");
        Integer line = messageTableMapper.updateByPrimaryKeySelective(messageTable);
        if(line>0){
            return ResultBean.returnOk();
        }
        return ResultBean.returnError();
    }


    @RequestMapping("/auth/messageTableUpdate")
    public ResultBean messageTableUpdate (Integer changeId,String messageStatus,String messageReply,String messageTime,String messageContent,String realName,String uname){

        MessageTable messageTable = new MessageTable();
        messageTable.setUname(uname);
        messageTable.setRealName(realName);
        messageTable.setMessageContent(messageContent);
//        messageTable.setMessageTime(new Date());
        messageTable.setMessageReply(messageReply);
        messageTable.setMessageStatus(messageStatus);
        messageTable.setId(changeId);
        Integer line = messageTableMapper.updateByPrimaryKeySelective(messageTable);
        if(line>0){
            return ResultBean.returnOk();
        }
        return ResultBean.returnError();
    }


    @RequestMapping("/auth/messageTableSelectList")
    public ResultBean messageTableSelectList (Integer page,Integer size){
        if(page == null){
            page = 1;
        }
        if(size == null){
            size = 10;
        }
        PageHelper.startPage(page,size);
        MessageTableExample example = new MessageTableExample();
        example.setOrderByClause("id desc");
        example.createCriteria().andMessageStatusEqualTo("正常");
        List<MessageTable> bsData = messageTableMapper.selectByExample(example);
        PageInfo<MessageTable> pageInfo = new PageInfo<>(bsData);
        long total = pageInfo.getTotal();
        List<MessageTable> list = pageInfo.getList();
        for (MessageTable messageTable : list) {
            if(messageTable.getMessageReply() == "" || messageTable.getMessageReply() == null){
                messageTable.setMessageReply("暂无回复");
            }
        }
        if(list==null || list.size()<=0){

            return ResultBean.returnError().pushMessage("暂时没有数据");
        }
        return ResultBean.returnOk().pushData("total",total).pushData("data",list);
    }


    @RequestMapping("/auth/messageTableSelectById")
    public ResultBean messageTableSelectById (Integer changeId){
        MessageTable messageTable = messageTableMapper.selectByPrimaryKey(changeId);
        return ResultBean.returnOk().pushData("data",messageTable);
    }


    @RequestMapping("/auth/healthKnowledgeTableInsert")
    public ResultBean healthKnowledgeTableInsert (String publishTime,String content,String category,String publisher,Integer uidc){
        BsUser user = userService.selectUserByUid(uidc);
        HealthKnowledgeTable healthKnowledgeTable = new HealthKnowledgeTable();
        healthKnowledgeTable.setPublisher(user.getUname());
        healthKnowledgeTable.setCategory(category);
        healthKnowledgeTable.setContent(content);
        healthKnowledgeTable.setPublishTime(new Date());
        Integer line = healthKnowledgeTableMapper.insertSelective(healthKnowledgeTable);
        if(line>0){
            return ResultBean.returnOk();
        }
        return ResultBean.returnError();
    }


    @RequestMapping("/auth/healthKnowledgeTableDeleteById")
    public ResultBean healthKnowledgeTableDeleteById (Integer bid){
        HealthKnowledgeTable healthKnowledgeTable = new HealthKnowledgeTable();
        healthKnowledgeTable.setId(bid);
        Integer line = healthKnowledgeTableMapper.deleteByPrimaryKey(bid);
        if(line>0){
            return ResultBean.returnOk();
        }
        return ResultBean.returnError();
    }


    @RequestMapping("/auth/healthKnowledgeTableUpdate")
    public ResultBean healthKnowledgeTableUpdate (Integer changeId,String publishTime,String content,String category,String publisher){

        HealthKnowledgeTable healthKnowledgeTable = new HealthKnowledgeTable();
        healthKnowledgeTable.setPublisher(publisher);
        healthKnowledgeTable.setCategory(category);
        healthKnowledgeTable.setContent(content);
        healthKnowledgeTable.setId(changeId);
        Integer line = healthKnowledgeTableMapper.updateByPrimaryKeySelective(healthKnowledgeTable);
        if(line>0){
            return ResultBean.returnOk();
        }
        return ResultBean.returnError();
    }


    @RequestMapping("/auth/healthKnowledgeTableSelectList")
    public ResultBean healthKnowledgeTableSelectList (Integer page,Integer size){
        if(page == null){
            page = 1;
        }
        if(size == null){
            size = 10;
        }
        PageHelper.startPage(page,size);
        HealthKnowledgeTableExample example = new HealthKnowledgeTableExample();
        example.setOrderByClause("id desc");
        List<HealthKnowledgeTable> bsData = healthKnowledgeTableMapper.selectByExample(example);
        PageInfo<HealthKnowledgeTable> pageInfo = new PageInfo<>(bsData);
        long total = pageInfo.getTotal();
        List<HealthKnowledgeTable> list = pageInfo.getList();
        if(list==null || list.size()<=0){

            return ResultBean.returnError().pushMessage("暂时没有数据");
        }
        return ResultBean.returnOk().pushData("total",total).pushData("data",list);
    }


    @RequestMapping("/auth/healthKnowledgeTableSelectById")
    public ResultBean healthKnowledgeTableSelectById (Integer changeId){
        HealthKnowledgeTable healthKnowledgeTable = healthKnowledgeTableMapper.selectByPrimaryKey(changeId);
        return ResultBean.returnOk().pushData("data",healthKnowledgeTable);
    }

    @RequestMapping("/auth/getHeMsg")
    public ResultBean getHeMsg (Integer uidc){
        PageHelper.startPage(1,1000);
        BsUser user = userService.selectUserByUid(uidc);
        DataTableExample example = new DataTableExample();
        example.createCriteria().andUnameEqualTo(user.getUname());
        example.setOrderByClause("id desc");

        List<DataTable> dataTables = dataTableMapper.selectByExample(example);
        PageInfo<DataTable> dataTablePageInfo = new PageInfo<>(dataTables);
        List<DataTable> list = dataTablePageInfo.getList();
        if(list==null || list.size()<=0){
            return ResultBean.returnError("未获取到您的数据，无法评估");
        }
        String tw = "正常";
        String xl = "正常";
        String xy = "正常";
        String jy = "";
        for (DataTable dataTable : list) {
            if(Float.parseFloat(dataTable.getBodyTemperature()) > 38.0){
                tw = "异常";
            }
            if(Float.parseFloat(dataTable.getHeartRate()) > 100){
                xl = "异常";
            }
            if(Float.parseFloat(dataTable.getBloodOxygen()) < 95){
                xy = "异常";
            }
        }
        if("异常".equals(tw)){
            jy = "体温过高，多观察孩子体温";
        }
        if("异常".equals(xl)){
            jy = jy + " | 心率过高，减少高强度运动";
        }
        if("异常".equals(xy)){
            jy = jy + " | 血氧过低，请及时就医";
        }
        if(jy.startsWith("|")){
            jy = jy.substring(1,jy.length());
        }
        if(jy.equals("")){
            jy = "保持良好的作息，适量运动，饮食均衡，注意休息。";
        }
        Hea hea = new Hea();
        hea.setTw(tw);
        hea.setJy(jy);
        hea.setXl(xl);
        hea.setXy(xy);
        return ResultBean.returnOk().pushData("data",hea);
    }


    @RequestMapping("setData")
    public ResultBean setData (String hr,String spo2,String temp){
        if(Float.parseFloat(hr) <=50 || Float.parseFloat(spo2) <=80  ){
            return ResultBean.returnOk();
        }
        BsUser user = userService.selectUserByUid(2);
        DataTable table = new DataTable();
        table.setUname(user.getUname());
        table.setChildName(user.getChildName());
        table.setHeartRate(hr);
        table.setBloodOxygen(spo2);
        table.setBodyTemperature(temp);
        table.setCtime(new Date());
        dataTableMapper.insertSelective(table);
        return ResultBean.returnOk();
    }









}
