# CSV File Data Extraction in Java

## Description

Is project mein hum Java ka use karke CSV (Comma-Separated Values) file se data kaise read karte hain, yeh bataya gaya hai. CSV file ek simple text file hoti hai jisme data rows aur columns mein hota hai, aur values comma (`,`) se separated hoti hain.

---

## How to Read CSV in Java

Java mein CSV read karne ke liye aap multiple libraries use kar sakte hain, jaise:

- **OpenCSV** (popular library)
- Plain Java (BufferedReader se)

### Example using OpenCSV Library

Sabse pehle, apne project mein OpenCSV library add karein (agar Maven use kar rahe hain toh):

```xml
<dependency>
    <groupId>com.opencsv</groupId>
    <artifactId>opencsv</artifactId>
    <version>5.7.1</version>
</dependency>
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVExample {
    public static void main(String[] args) {
        String csvFile = "data.csv";

        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                for (String value : line) {
                    System.out.print(value + " ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
Name,Age,City
Neeraj,25,Delhi
Samra,28,Mumbai
<img width="1438" height="564" alt="Screenshot 2025-08-29 123450" src="https://github.com/user-attachments/assets/c4785114-a30e-4609-8d4c-c94cf42490d5" />
<img width="1724" height="932" alt="Screenshot 2025-08-29 123430" src="https://github.com/user-attachments/assets/5e341c95-3ff3-42f6-ae63-fe5fc226c142" />
<img width="1536" height="906" alt="Screenshot 2025-08-29 123411" src="https://github.com/user-attachments/assets/eb23c9f2-b5f2-4b94-b680-8479dfc7b9cd" />
