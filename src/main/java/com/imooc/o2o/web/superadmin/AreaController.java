package com.imooc.o2o.web.superadmin;

import com.imooc.o2o.entity.Area;
import com.imooc.o2o.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zy on 2019/1/9
 */
@Controller
@RequestMapping(value = "/superadmin")
public class AreaController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/listarea", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listArea() {
        logger.info("==start==");
        long startTime = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>();
        List<Area> list = new ArrayList<>();
        try {
            list = areaService.getAreaList();
            map.put("rows", list);
            map.put("total", list.size());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("errMsg", e.toString());
        }
        long endTime = System.currentTimeMillis();
        logger.debug("cost time:[{}ms]",endTime-startTime);
        logger.info("==end==");
        return map;
    }

}
