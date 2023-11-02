package com.example.clotheswebsite.helper;

import com.example.clotheswebsite.entity.Role;
import com.example.clotheswebsite.entity.User;
import com.example.clotheswebsite.repository.RoleRepository;
import com.example.clotheswebsite.service.UserService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String SHEET = "Users";

    public static boolean hasExcelFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<User> excelToUsers(InputStream is, RoleRepository roleRepository) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();

            List<User> users = new ArrayList<User>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                User user = new User();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            user.setUsername(currentCell.getStringCellValue());
                            break;

                        case 1:
                            user.setPassword(currentCell.getStringCellValue());
                            break;

                        case 2:
                            user.setFullname(currentCell.getStringCellValue());
                            break;

                        case 3:
                            user.setGender((int) currentCell.getNumericCellValue());
                            break;

                        case 4:
                            user.setEmail(currentCell.getStringCellValue());
                            break;

                        case 5:
                            for (Role x : roleRepository.findAll()){
                                if (x.getRoleName().equals(currentCell.getStringCellValue())){
                                    user.setRole(x);
                                }
                            }
                            break;
                        default:
                            break;
                    }

                    cellIdx++;
                }

                users.add(user);

            }

            workbook.close();

            return users;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
