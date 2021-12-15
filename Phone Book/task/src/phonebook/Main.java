package phonebook;

import javax.naming.directory.SearchResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        File directory = new File("/Users/cassecou/Documents/directory.txt");
        File find = new File("/Users/cassecou/Documents/find.txt");

        long startTimeLinear = 0;
        long endTimeLinear = 0;
        long endTimeJumping = 0;
        long startBubleSort = 0;
        long endBubleSort = 0;
        long startQuickSort = 0;
        long endQuickSort = 0;
        long endTimeBinary = 0;
        long startHash = 0;
        long endHash = 0;
        long endTimeHashSearch = 0;
        Hashtable hashBook;
        Phonebook phonebook = new Phonebook(directory);
        System.out.println("Start searching(linear search)...");
        startTimeLinear = System.currentTimeMillis();
        long numberOfSearches = findNumberLinear(find, phonebook);
        endTimeLinear = System.currentTimeMillis();
        int msTime = (int) ((endTimeLinear - startTimeLinear) % 1000);
        int secTime = (int) ((endTimeLinear - startTimeLinear) / 1000) % 60;
        int minTime = (int) (((endTimeLinear - startTimeLinear) / 1000) / 60) % 60;
        System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.%n%n", numberOfSearches, numberOfSearches, minTime, secTime, msTime);
        System.out.println("Start searching(bubble sort + jump search)...");
        startBubleSort = System.currentTimeMillis();
        long timeLimit = 10 * (endTimeLinear - startTimeLinear);
        if (phonebook.bubleSort(timeLimit)) {
            endBubleSort = System.currentTimeMillis();
            numberOfSearches = findNumberJumping(find, phonebook);
            endTimeJumping = System.currentTimeMillis();
            msTime = (int) ((endTimeJumping - startBubleSort) % 1000);
            secTime = (int) ((endTimeJumping - startBubleSort) / 1000) % 60;
            minTime = (int) (((endTimeJumping - startBubleSort) / 1000) / 60) % 60;
            System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.%n", numberOfSearches, numberOfSearches, minTime, secTime, msTime);
            msTime = (int) ((endBubleSort - startBubleSort) % 1000);
            secTime = (int) ((endBubleSort - startBubleSort) / 1000) % 60;
            minTime = (int) (((endBubleSort - startBubleSort) / 1000) / 60) % 60;
            System.out.printf("Sorting time: %d min. %d sec. %d ms.%n", minTime, secTime, msTime);
            msTime = (int) ((endTimeJumping - endBubleSort) % 1000);
            secTime = (int) ((endTimeJumping - endBubleSort) / 1000) % 60;
            minTime = (int) (((endTimeJumping - endBubleSort) / 1000) / 60) % 60;
            System.out.printf("Searching time: %d min. %d sec. %d ms.%n%n", minTime, secTime, msTime);

        } else {
            endBubleSort = System.currentTimeMillis();
            startTimeLinear = System.currentTimeMillis();
            numberOfSearches = findNumberLinear(find, phonebook);
            endTimeLinear = System.currentTimeMillis();
            msTime = (int) ((endTimeLinear - startBubleSort) % 1000);
            secTime = (int) ((endTimeLinear - startBubleSort) / 1000) % 60;
            minTime = (int) (((endTimeLinear - startBubleSort) / 1000) / 60) % 60;
            System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.%n", numberOfSearches, numberOfSearches, minTime, secTime, msTime);
            msTime = (int) ((endBubleSort - startBubleSort) % 1000);
            secTime = (int) ((endBubleSort - startBubleSort) / 1000) % 60;
            minTime = (int) (((endBubleSort - startBubleSort) / 1000) / 60) % 60;
            System.out.printf("Sorting time: %d min. %d sec. %d ms. - STOPPED, moved to linear search%n", minTime, secTime, msTime);
            msTime = (int) ((endTimeLinear - startTimeLinear) % 1000);
            secTime = (int) ((endTimeLinear - startTimeLinear) / 1000) % 60;
            minTime = (int) (((endTimeLinear - startTimeLinear) / 1000) / 60) % 60;
            System.out.printf("Searching time: %d min. %d sec. %d ms.%n%n", minTime, secTime, msTime);

        }
        System.out.println("Start searching(quick sort + binary search)...");
        startQuickSort = System.currentTimeMillis();
        Phonebook phonebookQuick = new Phonebook(directory);
        phonebookQuick.quickSort(0,phonebookQuick.getRecords() - 1);
        endQuickSort = System.currentTimeMillis();
        numberOfSearches = findNumberBinary(find, phonebookQuick);
        endTimeBinary = System.currentTimeMillis();
        msTime = (int) ((endTimeBinary - startQuickSort) % 1000);
        secTime = (int) ((endTimeBinary - startQuickSort) / 1000) % 60;
        minTime = (int) (((endTimeBinary - startQuickSort) / 1000) / 60) % 60;
        System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.%n", numberOfSearches, numberOfSearches, minTime, secTime, msTime);
        msTime = (int) ((endQuickSort - startQuickSort) % 1000);
        secTime = (int) ((endQuickSort - startQuickSort) / 1000) % 60;
        minTime = (int) (((endQuickSort - startQuickSort) / 1000) / 60) % 60;
        System.out.printf("Sorting time: %d min. %d sec. %d ms.%n", minTime, secTime, msTime);
        msTime = (int) ((endTimeBinary - endQuickSort) % 1000);
        secTime = (int) ((endTimeBinary - endQuickSort) / 1000) % 60;
        minTime = (int) (((endTimeBinary - endQuickSort) / 1000) / 60) % 60;
        System.out.printf("Searching time: %d min. %d sec. %d ms.%n%n", minTime, secTime, msTime);

        System.out.println("Start searching (hash table)...");
        startHash = System.currentTimeMillis();
        hashBook = hashBook(directory);
        endHash = System.currentTimeMillis();
        numberOfSearches = findNumberHash(find, hashBook);
        endTimeHashSearch = System.currentTimeMillis();
        msTime = (int) ((endTimeHashSearch - startHash) % 1000);
        secTime = (int) ((endTimeHashSearch - startHash) / 1000) % 60;
        minTime = (int) (((endTimeHashSearch - startHash) / 1000) / 60) % 60;
        System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.%n", numberOfSearches, numberOfSearches, minTime, secTime, msTime);
        msTime = (int) ((endHash - startHash) % 1000);
        secTime = (int) ((endHash - startHash) / 1000) % 60;
        minTime = (int) (((endHash - startHash) / 1000) / 60) % 60;
        System.out.printf("Creating time: %d min. %d sec. %d ms.%n", minTime, secTime, msTime);
        msTime = (int) ((endTimeHashSearch - endHash) % 1000);
        secTime = (int) ((endTimeHashSearch - endHash) / 1000) % 60;
        minTime = (int) (((endTimeHashSearch - endHash) / 1000) / 60) % 60;
        System.out.printf("Searching time: %d min. %d sec. %d ms.%n%n", minTime, secTime, msTime);




    }

    static long findNumberLinear(File find, Phonebook phonebook) {
        try {
            Scanner findScanner = new Scanner(find);

            int i = 0;
            while (findScanner.hasNext()) {
                String searchName = findScanner.nextLine();
                phonebook.findNumberLinear(searchName);
                i++;
            }
            return i;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    static long findNumberJumping(File find, Phonebook phonebook) {
        Scanner findScanner = null;
        try {
            findScanner = new Scanner(find);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int i = 0;
        while (findScanner.hasNext()) {
            String searchName = findScanner.nextLine();
            phonebook.findNumberJump(searchName);
            i++;
        }
        return i;
    }

    static long findNumberBinary(File find, Phonebook phonebook) {
        try {
            Scanner findScanner = new Scanner(find);

            int i = 0;
            while (findScanner.hasNext()) {
                String searchName = findScanner.nextLine();
                phonebook.findNumberBinary(searchName);
                i++;
            }
            return i;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    static long findNumberHash(File find, Hashtable phonebook) {
        try {
            Scanner findScanner = new Scanner(find);

            int i = 0;
            while (findScanner.hasNext()) {
                String searchName = findScanner.nextLine();
                phonebook.get(searchName);
                i++;
            }
            return i;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }


    static Hashtable hashBook(File directory) {
        Hashtable phonebook = new Hashtable(2000000);
        try {
            Scanner directoryScanner = new Scanner(directory);
            while (directoryScanner.hasNext()) {
                String phone = directoryScanner.next();
                String name =  directoryScanner.nextLine().trim();
                phonebook.put(name, phone);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return phonebook;
    }
}

class Phonenumber {
    private String phone;
    private String name;

    public Phonenumber(String phone, String name){
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}

class Phonebook{
    private Phonenumber[] book;
    private int records;

    public Phonebook() {
        this.book = new Phonenumber[10000000];
        this.records = 0;
    }

    public Phonebook(File directory) {
        this();
        try {
            Scanner directoryScanner = new Scanner(directory);
            while (directoryScanner.hasNext()) {
                Phonenumber number = new Phonenumber(directoryScanner.next(), directoryScanner.nextLine().trim());
                this.addNumber(number);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addNumber(Phonenumber number){
        this.book[this.records] = new Phonenumber(number.getPhone(), number.getName());
        this.records += 1;
    }

    public int getRecords() {
        return records;
    }

    public void setRecords(int records) {
        this.records = records;
    }

    public Phonenumber[] getBook() {
        return book;
    }

    public void setBook(Phonenumber[] book, int records) {
        this.book = book;
        this.setRecords(records);
    }
    // TODO Вынести свап двух элементов в отдельный метод
    public String findNumberLinear(String name) {
        int searchResult = -1;

        for (int i = 0; i < this.getRecords(); i++) {
            if (this.getBook()[i].getName().equals(name)) {
                searchResult = i;
                break;
            }
        }
        if (searchResult >=0) {
            return this.book[searchResult].getPhone();
        } else {
            return "none";
        }
    }



    public String findNumberJump(String name) {
        int searchResult = -1;
        int jumpBlockLength = (int) Math.floor(Math.sqrt(this.getRecords()));
        int jump = 0;
        int blockSearchEnd;
        while (jump < this.getRecords() && this.getBook()[jump].getName().compareTo(name) < 0){
            jump = jump + jumpBlockLength;
        }
        if (jump == 0 && this.getBook()[jump].getName().compareTo(name) == 0) {
            searchResult = 0;
        }

        if (this.getBook()[this.getRecords() - 1].getName().compareTo(name) == 0){
            searchResult = this.getRecords() - 1;
        }

        if (this.getBook()[this.getRecords() - 1].getName().compareTo(name) > 0 && jump != 0){
            blockSearchEnd = Math.min(jump, this.getRecords() - 1);
            for (int i = blockSearchEnd; i > jump - jumpBlockLength; i--){
                if (this.getBook()[i].getName().compareTo(name) == 0) {
                    searchResult = i;
                    break;
                }
            }
        }


        if (searchResult >=0) {
            return this.book[searchResult].getPhone();
        } else {
            return "none";
        }
    }

    private int findNumberBinary(String name, int left, int right){
        int searchResult = -1;
        if (left <= right) {
            int middle = (left + right) / 2;
            if (this.getBook()[middle].getName().equals(name)) {
                searchResult = middle;
            } else if (this.getBook()[middle].getName().compareTo(name) > 0) {
                searchResult = this.findNumberBinary(name,left,middle - 1);
            } else {
                searchResult = this.findNumberBinary(name,middle + 1,right);
            }
        } else {
            searchResult = -1;
        }
        return searchResult;
    }

    public String findNumberBinary(String name) {
        int searchResult = findNumberBinary(name,0,this.getRecords() - 1);
        return  searchResult >= 0 ? this.getBook()[searchResult].getPhone() : "none";
    }

    public boolean bubleSort(long timeLimit) {
        boolean success = true;
        long startTime = System.currentTimeMillis();
        int upperbound = this.getRecords();
        for (int i = 0; i < this.getRecords(); i++){
            for (int j = 0; j < upperbound - 1; j++){
                if (this.getBook()[j].getName().compareTo(this.getBook()[j+1].getName()) > 0){
                    String name = this.getBook()[j+1].getName();
                    String number = this.getBook()[j+1].getPhone();
                    this.getBook()[j+1].setName(this.getBook()[j].getName());
                    this.getBook()[j+1].setPhone(this.getBook()[j].getPhone());
                    this.getBook()[j].setPhone(number);
                    this.getBook()[j].setName(name);
                }
            }
            if (System.currentTimeMillis() - startTime > timeLimit) {
                success = false;
                break;
            }
        }
        return success;
    }

    public int reorder(int left, int right) {
        String pivot = this.getBook()[right].getName();
        int index = left;
        for (int j = left; j < right; j++){
            if (this.getBook()[j].getName().compareTo(pivot) < 0){
                String name = this.getBook()[j].getName();
                String number = this.getBook()[j].getPhone();
                this.getBook()[j].setName(this.getBook()[index].getName());
                this.getBook()[j].setPhone(this.getBook()[index].getPhone());
                this.getBook()[index].setPhone(number);
                this.getBook()[index].setName(name);
                index++;
                }
        }
            String name = this.getBook()[right].getName();
            String number = this.getBook()[right].getPhone();
            this.getBook()[right].setName(this.getBook()[index].getName());
            this.getBook()[right].setPhone(this.getBook()[index].getPhone());
            this.getBook()[index].setPhone(number);
            this.getBook()[index].setName(name);



        return index;
    }

    public void quickSort(int begin, int end){
        if (begin < end) {
            int partIndex = this.reorder(begin, end);
            this.quickSort(begin, partIndex - 1);
            this.quickSort(partIndex + 1, end);
        }
    }

}
