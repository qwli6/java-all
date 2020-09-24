**ConcurrentHashMap 是一个线程安全且高效的 HashMap。**



在说 ConcurrentHashMap 之前，先看看 HashMap 和 HashTable 的区别。在并发编程中 HashMap 会导致程序死循环，而线程安全的 HashTable 在并发编程中效率比较低下。

