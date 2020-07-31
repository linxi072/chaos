package com.mezo.chaos.nyx.excel.controller;

import com.mezo.chaos.nyx.excel.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mezo
 *
 */
@Controller
public class StudentController {
    @Autowired
    private StudentService studentService;
    /**
     * 批量导入表单数据
     *
     * @param myFile
     * @return
     */

    @RequestMapping(value="/importExcel",method= RequestMethod.POST)
    public String importExcel(@RequestParam("myfile") MultipartFile myFile) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Integer num = studentService.importExcel(myFile);
        } catch (Exception e) {
            modelAndView.addObject("msg", e.getMessage());
            return "index";
        }
        modelAndView.addObject("msg", "数据导入成功");

        return "index";
    }

    /**
     * 导出excel
     * @param response
     */
    @RequestMapping(value="/exportExcel",method=RequestMethod.GET)
    public void exportExcel(HttpServletResponse response) {
        try {
            studentService.exportExcel(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
