package practic;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
	/*
	Напишите информационную систему "Корпорация".
Программа должна обеспечивать:
■ ввод данных;
■ редактирование данных сотрудника корпорации;
■ удаление сотрудника корпорации;
■ поиск сотрудника корпорации по фамилии;
■ вывод информации обо всех сотрудниках, указан-
ного возраста, или фамилия которых начинается на
указанную букву.
Организуйте возможность сохранения найденной
информации в файл.
Также весь список сотрудников корпорации сохраняется
в файл (при выходе из программы автоматически, в про-
цессе исполнения программы по команде пользователя).
При старте программы происходит загрузка списка
сотрудников корпорации из указанного пользователем
файла.
	 */
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        List<Persona> listPersons = new LinkedList<>();
        boolean stop = false;
        do {
            System.out.println("Добавить сотрудника-нажми--> 1\nРедактировать  сотрудника - нажми--> 2\nУдалить сотрудника - нажми--> 3\nПоиск сотрудника по фамилии - нажми--> 4\n" +
                    "Вывод информации обо всех сотрудниках указанного возрастата либо фамилии - нажми--> 5\nЗагрузить сохраненный список- нажми-->6\nЗакрыть программу-нажми -->0");
            String answer = reader.readLine();

            switch (Integer.parseInt(answer)) {
                case 1:
                    newPerson(listPersons);
                    break;
                case 2:
                    edit(listPersons);
                    break;
                case 3:
                    del(listPersons);
                    break;
                case 4:
                    searchLastName(listPersons);
                    break;
                case 5:
                    all(listPersons);
                    break;
                case 6:
                    downloadList();
                    break;
                case 0:
                    stop = true;
                    try (FileOutputStream fOs = new FileOutputStream(new File("list.txt"));
                         ObjectOutputStream objOs = new ObjectOutputStream(fOs);) {
                        objOs.writeObject(listPersons);
                        System.err.println("Файл сохранен в корне программы\"list.txt\"");
                    }
                    break;
            }
        } while (!stop);
    }

    public static void newPerson(List<Persona> listPersons) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String resume;
            do {
                System.out.println("Укажи имя сотрудника: ");
                String name = reader.readLine();
                System.out.println("Укажи возраст сотрудника: ");
                int income = Integer.parseInt(reader.readLine());
                listPersons.add(new Persona(name, income));
                System.out.println("Продолжить- нажми 0 ; Для выхода-любую клавишу");
                resume = reader.readLine();
            }
            while (resume.equals("0"));
        } catch (NumberFormatException ex) {
            System.err.println("Скорее всего указан возраст не в правильном формате. Рекомендованно целое чмсло от 0 до ...");
        }
    }

    public static void edit(List<Persona> listPersons) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Введи имя ");
            String name = reader.readLine();
            for (Persona p : listPersons) {
                if (p.getName().equals(name)) {
                    System.out.println("Укажи новое имя сотрудника: ");
                    String newName = reader.readLine();
                    System.out.println("Укажи  новый доход сотрудника: ");
                    int newIncome = Integer.parseInt(reader.readLine());
                    p.setAge(newIncome);
                    p.setName(newName);
                } else {
                    System.out.println("Такого сотрудника нет");
                }
            }
        } catch (NumberFormatException ex) {
            System.err.println("Скорее всего указан доход не в допустимом виде. Рекомендованно целое чмсло от 0 до ...");
        }
    }

    public static void del(List<Persona> listPersons) throws IOException {
        boolean flag = true;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введи имя ");
        String name = reader.readLine();
        for (Persona p : listPersons) {
            if (p.getName().equals(name)) {
                if (listPersons.remove(p)) {
                    System.out.println("Сотрудник удален");
                    flag = false;
                }
                if (flag == true) {
                    System.out.println("Не корректные данные, Либо такого сотрудника не существует");
                }
            }
        }
    }

    public static void searchLastName(List<Persona> listPersons) throws IOException {
        boolean flag = true;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введи имя ");
        String name = reader.readLine();
        for (Persona p : listPersons) {
            if (p.getName().equals(name)) {
                System.out.println(p);
                flag = false;
            }
            if (flag == false) {
                System.out.println("Не корректные данные, Либо такого сотрудника не существует");
            }
        }
    }

    public static void all(List<Persona> listPersons) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введи данные( возраст либо начальную букву фамилии): ");
        String answer = reader.readLine();
        if (Character.isDigit(answer.charAt(0))) {
            for (Persona p : listPersons) {
                if (p.getAge() == Integer.parseInt(answer)) {
                    System.out.println(p);
                }
            }
        }
        if (answer instanceof String) {
            for (Persona p : listPersons) {
                if (p.getName().charAt(0) == answer.charAt(0)) {
                    System.out.println(p);
                }
            }
        }
        System.out.println("Сохранить найдену информация в файл? 0-> да /прпустить-->любая клавиша");
        String userAnswer = reader.readLine();
        try {
            if (Integer.parseInt(userAnswer) == 0) {
                try (FileOutputStream fOs = new FileOutputStream(new File("search.txt"));
                     ObjectOutputStream objOs = new ObjectOutputStream(fOs);) {
                    for (Persona p : listPersons) {
                        if (p.getName().charAt(0) == answer.charAt(0))
                            objOs.writeObject(p);
                    }
                }
                System.out.println("Информация сохранена");
            }
        } catch (NumberFormatException ex) {
            System.out.println("Вы отказались от сохранения информации");

        }
    }

    public static void downloadList() {
        try (FileInputStream fIs = new FileInputStream("list.txt");
             ObjectInputStream objIs = new ObjectInputStream(fIs);) {
            List<Persona> listPersons = (List<Persona>) objIs.readObject();
            System.err.println("Данные загружены");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}



