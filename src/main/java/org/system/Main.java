package org.system;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println(
                    "===== STUDENT MANAGEMENT =====\n" + "\n" +
                            "1. Add Student\n" +
                            "2. View Student\n" +
                            "3. View All Students\n" +
                            "4. Update Student\n" +
                            "5. Delete Student\n" +
                            "6. Exit\n"
            );
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch(choice){
                case 1:
                    System.out.print("Enter name: ");
                    String name = sc.next();

                    System.out.print("Enter email: ");
                    String email = sc.next();

                    System.out.print("Enter course: ");
                    String course = sc.next();

                    System.out.print("Enter age: ");
                    int age = sc.nextInt();

                    Student student = new Student(name, email, course, age);

                    Session session = HibernateUtil
                            .getSessionFactory()
                            .openSession();

                    Transaction transaction = session.beginTransaction();
                    session.persist(student); //sql table
                    transaction.commit();
                    session.close();

                    System.out.println("Student added successfully!");
                    break;
                case 2:
                    System.out.print("Enter Student Id: ");
                    int id = sc.nextInt();

                    Session session1 = HibernateUtil
                            .getSessionFactory()
                            .openSession();

                    Student student1 = session1.find(Student.class, id);
                    if (student1 != null) {
                        System.out.println("\n=====View STUDENT =====");
                        System.out.println("ID: " + student1.getId());
                        System.out.println("Name: " + student1.getName());
                        System.out.println("Email: " + student1.getemail());
                        System.out.println("Course: " + student1.getCourse());
                        System.out.println("Age: " + student1.getAge());
                    } else {
                        System.out.println("Student not found!");
                    }

                    session1.close();
                    break;
                case 3:
                    Session session2 = HibernateUtil
                            .getSessionFactory()
                            .openSession();
                    List<Student> students = session2
                            .createQuery("FROM Student", Student.class)
                            .getResultList();
                    if (students.isEmpty()) {
                        System.out.println("No students found!");
                    } else {

                        System.out.println("\n===== ALL STUDENTS =====");

                        for (Student st : students) {
                            System.out.println(
                                    "ID: " + st.getId()
                                            + " | Name: " + st.getName()
                                            + " | Email: " + st.getemail()
                                            + " | Course: " + st.getCourse()
                                            + " | Age: " + st.getAge()
                            );
                        }
                    }

                    session2.close();
                    break;
                case 4:
                    System.out.print("Enter Student ID: ");
                    int ID = sc.nextInt();
                    sc.nextLine();

                    Session session3 = HibernateUtil
                            .getSessionFactory()
                            .openSession();

                    Transaction transaction1 = session3.beginTransaction();

                    Student st = session3.find(Student.class, ID);

                    if (st != null) {

                        System.out.println("\n===== UPDATE STUDENT =====");
                        System.out.println("1. Update Name");
                        System.out.println("2. Update Email");
                        System.out.println("3. Update Course");
                        System.out.println("4. Update Age");
                        System.out.print("Enter your choice: ");

                        int c = sc.nextInt();
                        sc.nextLine();

                        switch (c) {

                            case 1:
                                System.out.print("Enter new name: ");
                                String n = sc.nextLine();

                                st.setName(n);
                                break;

                            case 2:
                                System.out.print("Enter new email: ");
                                String e = sc.nextLine();

                                st.setemail(e);
                                break;

                            case 3:
                                System.out.print("Enter new course: ");
                                String courses = sc.nextLine();

                                st.setCourse(courses);
                                break;

                            case 4:
                                System.out.print("Enter new age: ");
                                int a = sc.nextInt();

                                st.setAge(a);
                                break;

                            default:
                                System.out.println("Invalid choice!");
                                transaction1.rollback();
                                session3.close();
                                break;
                        }

                        if (c >= 1 && c <= 4) {
                            transaction1.commit();
                            System.out.println("Student updated successfully!");
                        }

                    } else {

                        System.out.println("Student not found!");
                        transaction1.rollback();
                    }

                    session3.close();
                    break;
                case 5:

                    System.out.print("Enter Student ID: ");
                    int Id = sc.nextInt();

                    Session session4 = HibernateUtil
                            .getSessionFactory()
                            .openSession();

                    Transaction transaction2 = session4.beginTransaction();

                    Student s = session4.find(Student.class, Id);

                    if (s != null) {
                        session4.remove(s);
                        transaction2.commit();

                        System.out.println("Student deleted successfully!");
                    } else {
                        transaction2.rollback();
                        System.out.println("Student not found!");
                    }
                    session4.close();
                    break;
                case 6:
                    System.out.println("Close System");
                    sc.close();
                    return;
                default :
                    System.out.println("Not Found!");
                    break;
            }
        }
    }
}
