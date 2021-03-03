package manager.instruments.workerInstruments;

import java.util.HashSet;

/**
 * Класс для хранения и генерирования id работников.
 * @autor komyak9
 */
public class WorkerIdHolder {
    private static HashSet<Integer> idSet;

    static {
        idSet = new HashSet<>();
    }

    public static int createWorkerId() throws Exception {
        if (idSet.size() == Integer.MAX_VALUE)
            throw new Exception("The number of available id is empty.");

        boolean hasGapValue = false;
        int idToAdd = 0;

        for (int i = 1; i < idSet.size(); i++) {
            if (!idSet.contains(i)) {
                idSet.add(i);
                idToAdd = i;
                hasGapValue = true;
            }
        }
        if (!hasGapValue) {
            idSet.add(idSet.size() + 1);
            idToAdd = getIdSet().size();
        }
        return idToAdd;
    }

    public static HashSet<Integer> getIdSet() {
        return idSet;
    }

    public static void addId(int id) {
        idSet.add(id);
    }

    public static void remove(int id) {
        idSet.remove(id);
    }

    public static void clear() {
        idSet.clear();
    }
}
