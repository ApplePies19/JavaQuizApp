public interface QueueInterface<T> {
    public void push(T item);
    public T pop();
    public T front();
    public T back();
    public int size();
    public boolean empty();
}