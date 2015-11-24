package com.javachobo;

import java.util.Iterator;

class MyVector {
	
	protected Object[] data = null;//객체를 담기 위한 객체배열 선언
	protected int capacity = 0;//용량(객체배열의 크기)
	protected int size = 0;//객체배열에 저장된 객체의 개수
	
	public MyVector(int capacity){
	
		if(capacity < 0){
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
		data = new Object[capacity];

	}
	
	public MyVector(){
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

		if(this.capacity == newCapacity)return;
		
		Object[] tempData = new Object[newCapacity];
		System.arraycopy(data, 0, tempData, 0, size);
		data = tempData;
		capacity = newCapacity;
	}
	
	public boolean add(Object obj){
		//새로운 객체를 저장하기 전에 저장할 공간을 확보한 후에 객체를 추가해야한다.
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

		if(index > size){
			throw new ArrayIndexOutOfBoundsException();
		}
		Object temp = data[index];
		data[index] = obj;
		
		return temp;
	}
	
	public int indexOf(Object obj, int index){

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
		//indexof(obj, index)사용
		if(indexOf(obj, 0) == -1){
			return false;
		} else{
			return true;
		}
	}
	
	public int indexOf(Object obj){
		//indexof(obj, index)사용
		//찾기 시작할 위치를 지정하지 않으면 처음부터 찾는다.
		return indexOf(obj, 0);
	}
	
	public int lastIndexOf(Object obj){
		//lastindexof(obj, index)사용
		//찾기 시작할 위치를 지정하지 않으면 끝부터 찾는다.
		return lastIndexOf(obj, size-1);
	}
	
	public void add(int index, Object obj){

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
		Object[] tempArr = new Object[size];
		System.arraycopy(data, 0, tempArr, 0, size);
		
		return tempArr;
	}
	
	public String toString(){
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
		int cursor = 0;//읽어올 요소의 위치(index)
		int lastRet = -1;//직전에 읽어온 객체의 위치(index)
		
		public Object next(){
			Object obj = get(cursor);
			lastRet = cursor;
			cursor++;

			return obj;
		}
		
		public boolean hasNext(){

			if(cursor < size){
				return true;
			} else{
				return false;
			}

		}
		
		public void remove(){

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
