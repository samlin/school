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
        //        new ClassNameActionFreemarker(subject);

    }

    private static String[] getGeneralParametar() {

        String initializeData[] = { "interview", "" };
        return initializeData;
    }
}
