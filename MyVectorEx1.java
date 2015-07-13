package com.javachobo;

import java.util.Iterator;

class MyVector {
	
	protected Object[] data = null;//��ü�� ��� ���� ��ü�迭 ����
	protected int capacity = 0;//�뷮(��ü�迭�� ũ��)
	protected int size = 0;//��ü�迭�� ����� ��ü�� ����
	
	public MyVector(int capacity){
		 /*
	        1. �������� capacity�� ���� 0���� ������, IllegalArgumentExcepton�� �߻���Ų��.
	        2. �������� capacity�� ���� �ν��Ͻ����� capacity�� �����Ѵ�.
	        3. �������� capacity�� ���� ũ���� Object�迭�� �����ؼ� ��ü�迭 data�� �����Ѵ�.
	    */
		if(capacity < 0){
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
		data = new Object[capacity];

	}
	
	public MyVector(){
	/*
        1. �Ű������� ���� �����ڸ� ���� MyVector�� �����Ѵٸ�,
           	�뷮(capacity)�� 10�� �ǵ��� �Ѵ�.
           	�Ű������� �ִ� ������ MyVector(int capacity)�� ����ϼ���.
    */
		this(10);
	}
	
	public boolean isEmpty(){
		return size==0;
	}
	
	public int capacity(){
		return capacity;
	}
	
	public int size(){
		return size;
	}
	
	public void ensureCapacity(int minCapacity){
	/*
        1. minCapacity�� capacity���� ũ�� newCapacity�� ���� �ι�� �Ѵ�.
           (��� �ݵ�� 2���̾�� �� �ʿ�� ����. ������ ������ ũ�⸦ �÷��ֱ� ���� ����)
        2. �׷��� minCapacity�� newCapacity���� ũ��,
            minCapacity�� ���� newCapacity�� �����Ѵ�.
        3. setCapacity()�� ȣ���ؼ� ��ü�迭�� ũ�Ⱑ newCapacity�� �ǵ��� �Ѵ�.
    */
		int newCapacity = capacity;
		
		if(minCapacity > capacity){
			newCapacity *= 2;
			if(minCapacity > newCapacity){
				newCapacity = minCapacity;
			}
			setCapacity(newCapacity);
		}
	}
	
	public void setCapacity(int newCapacity){
	/*
        1. newCapacity ũ���� ��ü�迭�� ���� �����.
        2. ������ ��ü�迭(data)�� ������ ���ο� ��ü�迭�� �����Ѵ�.
           (System.arraycopy()���)
        3. data�� ���� ������ ��ü�迭�� �����ϵ��� �Ѵ�.
        4. capacity�� ���� newCapacity�� �����Ѵ�.
     */
		if(this.capacity == newCapacity)return;
		
		Object[] tempData = new Object[newCapacity];
		System.arraycopy(data, 0, tempData, 0, size);
		data = tempData;
		capacity = newCapacity;
	}
	
	public boolean add(Object obj){
		//���ο� ��ü�� �����ϱ� ���� ������ ������ Ȯ���� �Ŀ� ��ü�� �߰��ؾ��Ѵ�.
		int nextSize = size + 1;
		ensureCapacity(nextSize);
		data[size++] = obj;
		
		return true;
		
	}
	
	public Object get(int index){
		if(index<0 || index>=size){
			throw new IndexOutOfBoundsException();
		}
		return data[index];
	}
	
	public Object set(int index, Object obj){
/*
       1. index�� size���� ũ�� ArrayIndexOutOfBoundsException�� �߻���Ų��.
       2. ��ü�迭 data�� index��° ���� �ӽ÷� �����Ѵ�.
       3. ���ο� ��ü(obj)�� ��ü�迭 data�� index��° ���� �����Ѵ�.
       4. �ӽ÷� �����ߴ� ���� ��ü�� ��ȯ�Ѵ�.
  */
		if(index > size){
			throw new ArrayIndexOutOfBoundsException();
		}
		Object temp = data[index];
		data[index] = obj;
		
		return temp;
	}
	
	public int indexOf(Object obj, int index){
 /*
        1. �Ѱܹ��� ��ü(obj)�� null�̸�,
           1.1 �ݺ����� �̿��ؼ� ��ü�迭(data)���� null�� ���� ã�Ƽ� �� ��ġ�� ��ȯ�Ѵ�.
                (�˻������� index���� �����ؼ� �����ϴ� ����)
        2. �Ѱܹ��� ��ü(obj)�� null�� �ƴ� ��쿡��
           2.1 equals�� �̿��ؼ� ���� ��ü�� �ִ��� ã�Ƽ� �� ��ġ�� ��ȯ�Ѵ�.                
                (�˻������� index���� �����ؼ� �����ϴ� ����)
        3. ��ã���� -1�� ��ȯ�Ѵ�.
   */
		if(obj == null){
			for(int i=index; i<size; i++){
				if(data[i] == null){
					return i;
				}
			}
		} else{
			for(int i=index; i<size; i++){
				if(data[i].equals(obj)){
					return i;
				}
			}
		}
		return -1;

	}
	
	public int lastIndexOf(Object obj, int index){
	/*
         1. index�� ���� size���� ���ų� ũ��, IndexOutOfBoundsException�� �߻���Ų��.
         2. �Ѱܹ��� ��ü(obj)�� null�̸�,
            2.1 �ݺ����� �̿��ؼ� ��ü�迭(data)���� null�� ���� ã�Ƽ� �� ��ġ�� ��ȯ�Ѵ�.
                 (�˻������� index���� �����ؼ�, index���� ���ҽ��Ѽ� ��ü�迭�� 0��°���� )
         3. �Ѱܹ��� ��ü(obj)�� null�� �ƴ� ��쿡��
            3.1 equals�� �̿��ؼ� ���� ��ü�� �ִ��� ã�Ƽ� �� ��ġ�� ��ȯ�Ѵ�.                
                 (�˻������� index���� �����ؼ�, index���� ���ҽ��Ѽ� ��ü�迭�� 0��°���� )
         4. ��ã���� -1�� ��ȯ�Ѵ�.
    */
		if(index >= size){
			throw new IndexOutOfBoundsException();
		}
		if(obj == null){
			for(int i=index; i>=0; i--){
				if(data[i] == null){
					return i;
				}
			} 
		} else{
			for(int i=index; i>=0; i--){
				if(data[i].equals(obj)){
					return i;
				}
			}
		}
		return -1;
	}
	
	public boolean contains(Object obj){
		//indexof(obj, index)���
		if(indexOf(obj, 0) == -1){
			return false;
		} else{
			return true;
		}
	}
	
	public int indexOf(Object obj){
		//indexof(obj, index)���
		//ã�� ������ ��ġ�� �������� �������� ó������ ã�´�.
		return indexOf(obj, 0);
	}
	
	public int lastIndexOf(Object obj){
		//lastindexof(obj, index)���
		//ã�� ������ ��ġ�� �������� �������� ������ ã�´�.
		return lastIndexOf(obj, size-1);
	}
	
	public void add(int index, Object obj){
	/*
         1. index�� ���� size����ũ��, ArrayIndexOutOfBoundsException
         2. ensureCapacity()�� ȣ���ؼ� ���ο� ��ü�� ����� ������ Ȯ���Ѵ�.
         3. ��ü�迭���� index��ġ�� ��ü�� ������ ��ü���� ��ĭ�� ������ �̵��Ѵ�.
            (System.arraycopy()���)
         4. ��ü�迭�� index��ġ�� ���ο� ��ü(obj)�� �����Ѵ�.
         5. size�� ���� 1 ������Ų��.
    */
		if(index > size){
			throw new ArrayIndexOutOfBoundsException();
		}
		ensureCapacity(index);
		System.arraycopy(data, index, data, index+1, size-index);
		data[index] = obj;
		size++;

	}
	
	public Object remove(int index){
		Object oldObj = null;
	/*
        1. index�� �迭�� ������ ������� üũ�ϰ�, ����� IndexOutOfBoundsException�� �߻���Ų��.
        2. �����ϰ����ϴ� �����͸� oldObj�� �����Ѵ�.
        3. �����ϰ��� �ϴ� ��ü�� ������ ��ü�� �ƴ϶��, �迭���縦 ���� ���ڸ��� ä���ش�.
        4. ������ �����͸� null�� �Ѵ�. �迭�� 0 ���� �����ϹǷ� ������ ��Ҵ� index�� size-1�̴�. 
        5. size�� ���� 1 ���ҽ�Ų��.
        6. oldObj�� ��ȯ�Ѵ�.
   */
		if(index >= size){
			throw new IndexOutOfBoundsException();
		}
		oldObj = data[index];
		if(index != size-1){
			System.arraycopy(data, index+1, data, index, size-(index+1));
		}
		data[size-1] = null;
		size--;
		
		return oldObj;

	}
	
	public boolean remove(Object obj){
	/*
        1. �ݺ����� �̿��ؼ� ��ü�迭�� ��� ��ҿ� obj�� ��ġ�ϴ��� Ȯ���Ѵ�.
            1.1 ��ġ�ϸ� remove(int index)�� ȣ���ؼ� �����ϰ� true�� ��ȯ�Ѵ�.
            1.2 ��ġ�ϴ� ���� ã�� ���ϸ�, false�� ��ȯ�Ѵ�.
     */
		boolean check = false;
		for(int i=0; i<size; i++){
			if(obj.equals(data[i])){
				remove(i);
				check = true;
			}
		}
		
		return check;
	}
	
	public void clear(){
		for(int i=0; i<size; i++){
			data[i] = null;
		}
		size = 0;
	}
	
	public Object[] toArray(){
	/*
        1. ��ü�迭 data�� ���� ũ���� ��ü�迭�� �����Ѵ�.
        2. �迭�� ������ �����Ѵ�. (System.arraycopy()���)
        3. ������ ��ü�迭�� ��ȯ�Ѵ�.
    */
		Object[] tempArr = new Object[size];
		System.arraycopy(data, 0, tempArr, 0, size);
		
		return tempArr;
	}
	
	public String toString(){
	/*
        1. StringBuffer�� �����Ѵ�.
        2. �ݺ����� get(int i)�� ����ؼ� �迭�� ��� ��ҿ� �����ؼ� toString()�� ȣ���ؼ�
           sb�� �����Ѵ�.
        3. sb�� String���� ��ȯ�ؼ� ��ȯ�Ѵ�.
   */
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(int i=0; i<size; i++){
			sb.append(get(i));
			if(i != size-1){
				sb.append(", ");
			}
		}
		sb.append("]");
		
		return sb.toString();
	}
	
	public Iterator iterator(){
		return new Itr();
	}
	
	private class Itr implements Iterator {
		int cursor = 0;//�о�� ����� ��ġ(index)
		int lastRet = -1;//������ �о�� ��ü�� ��ġ(index)
		
		public Object next(){
		/*
            1. cursor�� ����Ű�� �ִ� ��ġ(index)�� ��ü�� �����´�.(get()���)
            2. cursor�� ���� lastRet�� �����ϰ� cursor�� ���� 1 ������Ų��.
               (���� ��� cursor�� ���� 1 �̾����� lastRet�� ���� 1�� �ǰ�, cursor�� ���� 2�� �ȴ�.)
            3. 1���� ������ ��ü�� ��ȯ�Ѵ�.
         */
			Object obj = get(cursor);
			lastRet = cursor;
			cursor++;

			return obj;
		}
		
		public boolean hasNext(){
		 /*
             hint : cursor�� ���� ��ü�迭�� ����������� ��ġ(index)�� �ٴٶ����� Ȯ���Ѵ�.
        */ 
			if(cursor < size){
				return true;
			} else{
				return false;
			}

		}
		
		public void remove(){
		/*
            1. lastRet�� ���� -1�̸�(������ �о�� ��ü�� ���ų� ���� �Ǿ�����)
               IllegalStateException�� �߻���Ų��.
            2. ������ �о�� ��ü�� ��ü�迭���� �����Ѵ�.(MyVector�� remove()���)
            3. lastRet�� ���� cursor�� ������ ������ cursor�� ���� 1���� ��Ų��.
                (���� cursor�� ��ġ���� ������ ���� �����Ǹ� cursor�� ��ġ�� ����Ǿ�� �ϹǷ�)
            4. lastRet�� -1�� �����Ѵ�.(������ �о�� ��ü�� �����Ǿ����Ƿ�)

            ���� ��� next()�� ȣ���ؼ� ��ü�迭�� index�� 3�� ��Ҹ� �о����..
             cursor(������ �о�� ��ü�� ��ġ)�� ���� 4�� �ǰ�, lastRet�� ���� 3�� �ȴ�.

             �̶� remove()�� ȣ��Ǹ�, �о�� ��ü�� index�� 3�� ��Ҵ� ���� �ǰ�
             index�� 4�� ��ġ�� �ִ� ��ü�� index�� 3�� ��ġ�� �̵��ϰ� �ȴ�.
             (�� �ڸ��� �޲ٱ� ���� index�� 4 ������ ��� ��ü�� �̵��ؾ���)

             �׷��� cursor�� ���� 4���� 3�� �Ǿ�� �ϰ�, lastRet�� ���� -1�� �Ǿ
             �о�� ��ü�� ���ų� ���� �Ǿ����� �ǹ��Ѵ�.                     
        */
			if(lastRet == -1){
				throw new IllegalStateException();
			}
			MyVector.this.remove(lastRet);
			cursor--;
			lastRet = -1;

		}
	}

}

public class MyVectorEx1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyVector v = new MyVector(2);
		
		v.add("AAA");
		v.add("BBB");
		v.add("CCC");
		v.add("DDD");
		
		Iterator it = v.iterator();
		
		while(it.hasNext()){
			Object obj = it.next();
			System.out.println(obj);
			
			if(obj.equals("BBB")){
				it.remove();
			}
		}	
		System.out.println(v);
	}

}
