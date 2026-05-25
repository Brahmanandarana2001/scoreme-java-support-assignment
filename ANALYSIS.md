# Task 2 Analysis

## 1. What is the exact cause of ConcurrentModificationException in Java?

ConcurrentModificationException occurs when a collection is modified structurally while it is being iterated using an Iterator or enhanced for-loop, except through the Iterator’s own remove() method.

## 2. What code pattern at line 142 most likely triggered this error?

Most likely pattern:

```java
for (Transaction t : transactions) {
    if (condition) {
        transactions.remove(t);
    }
}
```

## 3. Provide the minimal code change that resolves this safely.

```java
Iterator<Transaction> iterator = transactions.iterator();

while (iterator.hasNext()) {
    Transaction t = iterator.next();

    if (condition) {
        iterator.remove();
    }
}
```