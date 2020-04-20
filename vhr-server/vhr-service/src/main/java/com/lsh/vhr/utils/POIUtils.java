package com.lsh.vhr.utils;

import com.lsh.vhr.model.*;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class POIUtils {

    public static ResponseEntity<byte[]> employee2Excel(List<Employee> list) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建文档摘要
        workbook.createInformationProperties();
        //获取并配置文档摘要信息
        DocumentSummaryInformation docInfo = workbook.getDocumentSummaryInformation();
        docInfo.setCategory("员工信息");
        docInfo.setManager("javagirl");
        docInfo.setCompany("梁少华公司");
        //获取文档摘要信息
        SummaryInformation summInfo = workbook.getSummaryInformation();
        summInfo.setTitle("员工信息表");
        summInfo.setAuthor("javaboy");
        summInfo.setComments("文档由javaboy提供");

        HSSFCellStyle headerStyle=workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        HSSFCellStyle dateCellStyle=workbook.createCellStyle();
        dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));

        HSSFSheet sheet = workbook.createSheet("员工信息表");
        sheet.setColumnWidth(0,5*256);
        sheet.setColumnWidth(1,12*256);
        sheet.setColumnWidth(2,10*256);
        sheet.setColumnWidth(3,5*256);
        sheet.setColumnWidth(4,12*256);
        sheet.setColumnWidth(5,20*256);
        sheet.setColumnWidth(6,10*256);
        sheet.setColumnWidth(7,10*256);
        sheet.setColumnWidth(8,16*256);
        sheet.setColumnWidth(9,12*256);
        sheet.setColumnWidth(10,15*256);
        sheet.setColumnWidth(11,20*256);
        sheet.setColumnWidth(12,16*256);
        sheet.setColumnWidth(13,14*256);
        sheet.setColumnWidth(14,14*256);
        sheet.setColumnWidth(15,12*256);
        sheet.setColumnWidth(16,8*256);
        sheet.setColumnWidth(17,20*256);
        sheet.setColumnWidth(18,20*256);
        sheet.setColumnWidth(19,12*256);
        sheet.setColumnWidth(20,8*256);
        sheet.setColumnWidth(21,25*256);
        sheet.setColumnWidth(22,14*256);
        sheet.setColumnWidth(23,15*256);
        sheet.setColumnWidth(24,15*256);
        sheet.setColumnWidth(25,15*256);

        HSSFRow r0 = sheet.createRow(0);
        HSSFCell c0 = r0.createCell(0);
        c0.setCellStyle(headerStyle);
        c0.setCellValue("编号");
        HSSFCell c1 = r0.createCell(1);
        c1 .setCellStyle(headerStyle);
        c1.setCellValue("姓名");
        HSSFCell c2 = r0.createCell(2);
        c2 .setCellStyle(headerStyle);
        c2.setCellValue("工号");
        HSSFCell c3 = r0.createCell(3);
        c3 .setCellStyle(headerStyle);
        c3.setCellValue("性别");
        HSSFCell c4 = r0.createCell(4);
        c4 .setCellStyle(headerStyle);
        c4.setCellValue("出生日期");
        HSSFCell c5 = r0.createCell(5);
        c5 .setCellStyle(headerStyle);
        c5.setCellValue("身份证号码");
        HSSFCell c6 = r0.createCell(6);
        c6 .setCellStyle(headerStyle);
        c6.setCellValue("婚姻状况");
        HSSFCell c7 = r0.createCell(7);
        c7 .setCellStyle(headerStyle);
        c7.setCellValue("民族");
        HSSFCell c8 = r0.createCell(8);
        c8 .setCellStyle(headerStyle);
        c8.setCellValue("籍贯");
        HSSFCell c9 = r0.createCell(9);
        c9 .setCellStyle(headerStyle);
        c9.setCellValue("政治面貌");
        HSSFCell c10 = r0.createCell(10);
        c10.setCellStyle(headerStyle);
        c10.setCellValue("电话号码");
        HSSFCell c11 = r0.createCell(11);
        c11.setCellStyle(headerStyle);
        c11.setCellValue("联系地址");
        HSSFCell c12 = r0.createCell(12);
        c12.setCellStyle(headerStyle);
        c12.setCellValue("所属部门");
        HSSFCell c13 = r0.createCell(13);
        c13.setCellStyle(headerStyle);
        c13.setCellValue("职称");
        HSSFCell c14 = r0.createCell(14);
        c14.setCellStyle(headerStyle);
        c14.setCellValue("职位");
        HSSFCell c15 = r0.createCell(15);
        c15.setCellStyle(headerStyle);
        c15.setCellValue("聘用形式");
        HSSFCell c16 = r0.createCell(16);
        c16.setCellStyle(headerStyle);
        c16.setCellValue("最高学历");
        HSSFCell c17 = r0.createCell(17);
        c17.setCellStyle(headerStyle);
        c17.setCellValue("专业");
        HSSFCell c18 = r0.createCell(18);
        c18.setCellStyle(headerStyle);
        c18.setCellValue("毕业院校");
        HSSFCell c19 = r0.createCell(19);
        c19.setCellStyle(headerStyle);
        c19.setCellValue("入职日期");
        HSSFCell c20 = r0.createCell(20);
        c20.setCellStyle(headerStyle);
        c20.setCellValue("在职状态");
        HSSFCell c21 = r0.createCell(21);
        c21.setCellStyle(headerStyle);
        c21.setCellValue("邮箱");
        HSSFCell c22 = r0.createCell(22);
        c22.setCellStyle(headerStyle);
        c22.setCellValue("合同期限（年）");
        HSSFCell c23 = r0.createCell(23);
        c23.setCellStyle(headerStyle);
        c23.setCellValue("合同起始日期");
        HSSFCell c24 = r0.createCell(24);
        c24.setCellStyle(headerStyle);
        c24.setCellValue("合同中止日期");
        HSSFCell c25 = r0.createCell(25);
        c25.setCellStyle(headerStyle);
        c25.setCellValue("转正日期");

        for (int i = 0; i < list.size(); i++) {
            Employee emp = list.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(emp.getId());
            row.createCell(1).setCellValue(emp.getName());
            row.createCell(2).setCellValue(emp.getWorkID());
            row.createCell(3).setCellValue(emp.getGender());
            HSSFCell cell4 = row.createCell(4);
            cell4.setCellStyle(dateCellStyle);
            cell4.setCellValue(emp.getBirthday());
            row.createCell(5).setCellValue(emp.getIdCard());
            row.createCell(6).setCellValue(emp.getWedlock());
            row.createCell(7).setCellValue(emp.getNation().getName());
            row.createCell(8).setCellValue(emp.getNativePlace());
            row.createCell(9).setCellValue(emp.getPoliticsstatus().getName());
            row.createCell(10).setCellValue(emp.getPhone());
            row.createCell(11).setCellValue(emp.getAddress());
            row.createCell(12).setCellValue(emp.getDepartment().getName());
            row.createCell(13).setCellValue(emp.getJobLevel().getName());
            row.createCell(14).setCellValue(emp.getPosition().getName());
            row.createCell(15).setCellValue(emp.getEngageForm());
            row.createCell(16).setCellValue(emp.getTiptopDegree());
            row.createCell(17).setCellValue(emp.getSpecialty());
            row.createCell(18).setCellValue(emp.getSchool());
            HSSFCell cell19 = row.createCell(19);
            cell19.setCellStyle(dateCellStyle);
            cell19.setCellValue(emp.getBeginDate());
            row.createCell(20).setCellValue(emp.getWorkState());
            row.createCell(21).setCellValue(emp.getEmail());
            row.createCell(22).setCellValue(emp.getContractTerm());
            HSSFCell cell23 = row.createCell(23);
            cell23.setCellStyle(dateCellStyle);
            cell23.setCellValue(emp.getBeginContract());
            HSSFCell cell24 = row.createCell(24);
            cell24.setCellStyle(dateCellStyle);
            cell24.setCellValue(emp.getEndContract());
            HSSFCell cell25 = row.createCell(25);
            cell25.setCellStyle(dateCellStyle);
            cell25.setCellValue(emp.getConversionTime());
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        HttpHeaders headers=new HttpHeaders();
        try {
            headers.setContentDispositionFormData("attachment",
                    new String("员工表.xls".getBytes("UTF-8"),"ISO-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            workbook.write(baos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<byte[]>(baos.toByteArray(),headers, HttpStatus.CREATED);
    }

    /**
     * Excel解析成员工数据结合
     * @param file
     * @param allNations
     * @param allPoliticsstatus
     * @param allDepartments
     * @param allPositions
     * @param allJobLevels
     * @return
     */
    public static List<Employee> excel2Employee(MultipartFile file, List<Nation> allNations, List<Politicsstatus> allPoliticsstatus, List<Department> allDepartments, List<Position> allPositions, List<JobLevel> allJobLevels) {
        List<Employee> list=new ArrayList<>();
        Employee emp=null;
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i <numberOfSheets ; i++) {
                HSSFSheet sheet = workbook.getSheetAt(i);
                int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
                for (int j = 0; j <physicalNumberOfRows ; j++) {
                    //跳过标题行
                    if (j==0){
                        continue;//跳过标题行
                    }
                    HSSFRow row = sheet.getRow(j);
                    if (row==null){
                        continue;//防止中间有空行
                    }
                    int physicalNumberOfCells = row.getPhysicalNumberOfCells();
                    emp=new Employee();
                    for (int k = 0; k < physicalNumberOfCells; k++) {

                        HSSFCell cell = row.getCell(k);
                        switch (cell.getCellType()){
                            case STRING:
                                String cellValue = cell.getStringCellValue();
                                switch (k){
                                    case 1:
                                        emp.setName(cellValue);
                                        break;
                                    case 2:
                                        emp.setWorkID(cellValue);
                                        break;
                                    case 3:
                                        emp.setGender(cellValue);
                                        break;
                                    case 5:
                                        emp.setIdCard(cellValue);
                                        break;
                                    case 6:
                                        emp.setWedlock(cellValue);
                                        break;
                                    case 7:
                                        int nationIndex=allNations.indexOf(new Nation(cellValue));
                                        emp.setNationId(allNations.get(nationIndex).getId());
                                        break;
                                    case 8:
                                        emp.setNativePlace(cellValue);
                                        break;
                                    case 9:
                                        int politicsstatusIndex=allPoliticsstatus.indexOf(new Politicsstatus(cellValue));
                                        emp.setPoliticId(allPoliticsstatus.get(politicsstatusIndex).getId());
                                        break;
                                    case 10:
                                        emp.setPhone(cellValue);
                                        break;
                                    case 11:
                                        emp.setAddress(cellValue);
                                        break;
                                    case 12:
                                        int departmentIndex=allDepartments.indexOf(new Department(cellValue));
                                        emp.setDepartmentId(allDepartments. get(departmentIndex).getId());
                                        break;
                                    case 13:
                                        int jobLevelIndex=allJobLevels.indexOf(new JobLevel(cellValue));
                                        emp.setJobLevelId(allJobLevels.get(jobLevelIndex).getId());
                                        break;
                                    case 14:
                                        int positionsIndex=allPositions.indexOf(new Position(cellValue));
                                        emp.setPosId(allPositions.get(positionsIndex).getId());
                                        break;
                                    case 15:
                                        emp.setEngageForm(cellValue);
                                        break;
                                    case 16:
                                        emp.setTiptopDegree(cellValue);
                                        break;
                                    case 17:
                                        emp.setSpecialty(cellValue);
                                        break;
                                    case 18:
                                        emp.setSchool(cellValue);
                                        break;
                                    case 20:
                                        emp.setWorkState(cellValue);
                                        break;
                                    case 21:
                                        emp.setEmail(cellValue);
                                        break;

                                }
                                break;
                            default:{
                                Date dateCellValue = cell.getDateCellValue();
                                switch (k){
                                    case 4:
                                        emp.setBirthday(dateCellValue);
                                        break;
                                    case 19:
                                        emp.setBeginDate(dateCellValue);
                                        break;
                                    case 22:
                                        emp.setContractTerm(cell.getNumericCellValue());
                                        break;
                                    case 23:
                                        emp.setBeginContract(dateCellValue);
                                        break;
                                    case 24:
                                        emp.setEndContract(dateCellValue);
                                        break;
                                    case 25:
                                        emp.setConversionTime(dateCellValue);
                                        break;
                                }
                            }
                            break;
                        }

                    }
    
                    list.add(emp);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
