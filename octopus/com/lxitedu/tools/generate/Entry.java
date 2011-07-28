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
    // Scanner scanner = new Scanner(System.in); // �õ��������
    // StringBuffer initializeDataStrBuffer = new StringBuffer(); //
    // ��ʼ�����ݷŷ�StringBuffer��
    // initializeDataStrBuffer.append(scanner.next()); // �̶߳���

    // String initializeData[] = new
    // String[initializeDataStrBuffer.toString().split(Constant.SEPARATE).length];//
    // �����ݴ�С�õ���Ӧ��С����
    // initializeData =
    // initializeDataStrBuffer.toString().split(Constant.SEPARATE);// ���ݷ�������

    String initializeData[] = { "user", "dokeos" };
    return initializeData;
  }
}
