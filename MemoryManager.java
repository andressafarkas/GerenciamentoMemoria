import java.util.LinkedList;

public class MemoryManager {
    private LinkedList<MemoryPartition> memory;

    public MemoryManager(int size) {
        memory = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            memory.add(new MemoryPartition());
        }
    }

    public boolean bestFit(char processId, int processSize){
        int bestIndex = -1;
        int bestFitDifference = Integer.MAX_VALUE;
        int currentLength = 0;
        int startIndex = 0;
    
        for (int i = 0; i < memory.size(); i++) {
            if (!memory.get(i).isOccupied()) {
                if (currentLength == 0) {
                    startIndex = i;
                }
                currentLength++;
            } else {
                if (currentLength >= processSize) {
                    int currentDifference = currentLength - processSize;
                    if (currentDifference < bestFitDifference) {
                        bestFitDifference = currentDifference;
                        bestIndex = startIndex;
                    }
                }
                currentLength = 0;  // Reset current length.
            }
        }
    
        // Considerando o caso em que o último bloco na memória é um espaço vazio:
        if (currentLength >= processSize) {
            int currentDifference = currentLength - processSize;
            if (currentDifference < bestFitDifference) {
                bestIndex = startIndex;
            }
        }
    
        if (bestIndex != -1) {
            for (int i = bestIndex; i < bestIndex + processSize; i++) {
                memory.get(i).allocate(processId);
            }
            return true;
        }
    
        return false;  // Não encontrou um espaço adequado para alocação.
        }
    

    public boolean firstFit(char processId, int processSize) {
            int currentLength = 0;
            int startIndex = -1;
        
            for (int i = 0; i < memory.size(); i++) {
                if (!memory.get(i).isOccupied()) {
                    if (currentLength == 0) {
                        startIndex = i;  // Começamos a contar um novo espaço vazio.
                    }
                    currentLength++;
        
                    // Se o espaço vazio contíguo que encontramos é grande o suficiente, pare.
                    if (currentLength == processSize) {
                        break;
                    }
                } else {
                    currentLength = 0;  // Resetamos currentLength porque encontramos um bloco ocupado.
                }
            }
        
            // Se encontramos um espaço adequado:
            if (currentLength >= processSize) {
                for (int i = startIndex; i < startIndex + processSize; i++) {
                    memory.get(i).allocate(processId);
                }
                return true;
            }
        
            return false;  // Não encontrou um espaço adequado para alocação.
       
    }
}        