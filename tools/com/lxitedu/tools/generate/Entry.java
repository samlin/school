package com.lxitedu.tools.generate;

import com.lxitedu.tools.generate.subject.GeneralSubject;

public class Entry {
    public static void main(String[] args) {
        GeneralSubject subject = new GeneralSubject();
        String[] initializeData = getGeneralParametar();

        generalAll(subject);

        try {
            subject.toNotify(initializeData);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void generalAll(GeneralSubject subject) {
        new GenerateBean(subject);
        //        new ClassNameDaoFreemarker(subject);
        //        new ClassNameDaoImplFreemarker(subject);
        //        new ClassNameServiceFreemarker(subject);
        //        new ClassNameServiceImplFreemarker(subject);

    }

    private static String[] getGeneralParametar() {
        // Scanner scanner = new Scanner(System.in); // 得到输入对像
        // StringBuffer initializeDataStrBuffer = new StringBuffer(); //
        // 初始化数据放放StringBuffer里
        // initializeDataStrBuffer.append(scanner.next()); // 线程堵塞

        // String initializeData[] = new
        // String[initializeDataStrBuffer.toString().split(Constant.SEPARATE).length];//
        // 按类容大小得到相应大小数组
        // initializeData =
        // initializeDataStrBuffer.toString().split(Constant.SEPARATE);// 数据放入数组

        String initializeData[] = { "homework", "" };
        return initializeData;
    }
}
