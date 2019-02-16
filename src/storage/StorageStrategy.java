package storage;

class StorageStrategy {

    private Storage strategy;

    void setStrategy(Storage strategy) {
        this.strategy = strategy;
    }

    Storage getStrategy() {
        return strategy;
    }
}
