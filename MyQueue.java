import java.util.ArrayList;

public class MyQueue<T> implements QueueInterface<T>{

    private static final int MAX_SIZE = 50;

    ArrayList<T> MyArray = new ArrayList<>(MAX_SIZE);
    int front = 0;
    int back = -1;

    @Override
    public void push(T item){
        if (MyArray.size() == MAX_SIZE){
            System.out.println("Queue Overflow");
            return;
        }
        MyArray.add(item);
        back++;
    }

    @Override
    public T pop(){
        if (empty()){
            System.out.println("Queue Underflow");
            return null;
        }
        T item = MyArray.get(front);
        MyArray.remove(front);
        back--;
        return item;
    }

    @Override
    public T front(){
        return MyArray.get(front);
    }

    @Override
    public T back(){
        if (empty()){
            System.out.println("Queue Underflow");
            return null;
        }
        return MyArray.get(back);
    }

    @Override
    public int size(){
        return MyArray.size();
    }

    @Override
    public boolean empty(){
        return MyArray.size() == 0;
    }
}
