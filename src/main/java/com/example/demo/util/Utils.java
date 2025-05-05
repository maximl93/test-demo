package com.example.demo.util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class Utils {

    private static final Random random = new Random();

    public List<Integer> extract(String filePath) {
        List<Integer> numbers = new ArrayList<>();

        try(OPCPackage pkg = OPCPackage.open(new File(filePath)))  {
            XSSFWorkbook workbook = new XSSFWorkbook(pkg);

            XSSFSheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                double number = row.getCell(0).getNumericCellValue();
                numbers.add((int) number);
            }
        } catch (InvalidFormatException | IOException e) {
            throw new RuntimeException(e);
        }

        return numbers;
    }

    public int findNthMinimum(String filePath, int n) {
        List<Integer> numbers = extract(filePath);

        if (n > numbers.size()) {
            throw new IllegalArgumentException("n больше размера списка чисел");
        }
        return quickselect(numbers, 0, numbers.size() - 1, n - 1);
    }

    private int quickselect(List<Integer> numbers, int left, int right, int k) {
        if (left == right) {
            return numbers.get(left);
        }

        int pivotIndex = partition(numbers, left, right);

        if (k == pivotIndex) {
            return numbers.get(k);
        } else if (k < pivotIndex) {
            return quickselect(numbers, left, pivotIndex - 1, k);
        } else {
            return quickselect(numbers, pivotIndex + 1, right, k);
        }
    }

    private int partition(List<Integer> numbers, int left, int right) {
        int pivotIndex = left + random.nextInt(right - left + 1);
        int pivotValue = numbers.get(pivotIndex);
        swap(numbers, pivotIndex, right);
        int storeIndex = left;

        for (int i = left; i < right; i++) {
            if (numbers.get(i) < pivotValue) {
                swap(numbers, storeIndex, i);
                storeIndex++;
            }
        }
        swap(numbers, storeIndex, right);
        return storeIndex;
    }

    private void swap(List<Integer> numbers, int i, int j) {
        Integer temp = numbers.get(i);
        numbers.set(i, numbers.get(j));
        numbers.set(j, temp);
    }
}
