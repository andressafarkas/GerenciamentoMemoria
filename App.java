import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class App {
    static LinkedList<Process> memory = new LinkedList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Informe o tamanho da memória (apenas potências de 2): ");
        int memorySize = scanner.nextInt();
        MemoryManager memoryManager = new MemoryManager(memorySize);

        System.out.print("Escolha a estratégia de alocação (1 - First-Fit, 2 - Worst-Fit, 3 - Best-Fit, 4 - Circular-Fit): ");
        int choice = scanner.nextInt();

        List<String> instructions = readFile("arquivo_teste.txt");

        switch (choice) {
            case 1:
                System.out.println("Usando First-Fit:");
                memoryManager.executeInstructions(instructions, 1);
                break;
            case 2:
                System.out.println("Usando Worst-Fit:");
                memoryManager.executeInstructions(instructions, 2);
                break;
            case 3:
                System.out.println("Usando Best-Fit:");
                memoryManager.executeInstructions(instructions, 3);
                break;
            case 4:
                System.out.println("Usando Circular-Fit:");
                memoryManager.executeInstructions(instructions, 4);
                break;
            default:
                System.out.println("Escolha inválida.");
        }

        scanner.close();
    }

    static List<String> readFile(String filename) {
        List<String> instructions = new ArrayList<>();
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                instructions.add(line);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado: " + e.getMessage());
        }
        return instructions;
    }
}
