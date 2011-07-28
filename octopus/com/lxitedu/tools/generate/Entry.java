package com.lxitedu.tools.generate;

import com.lxitedu.tools.generate.subject.Subject;

public class Entry {
  public static void main(String[] args) {
    Subject subject = new Subject();
    String[] initializeData = getGeneralParametar();

    // generalAll(subject);
     new GenerateBean(subject);
     new GenerateDAO(subject);
    // //

    try {
      subject.toNotify(initializeData);
    } catch (Exception e) {
      e.printStackTrace();
    }

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

    String initializeData[] = { "user", "dokeos" };
    return initializeData;
  }
}
