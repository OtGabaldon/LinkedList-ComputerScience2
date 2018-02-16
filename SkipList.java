// Ot Gabaldon Torrents
// COP 3402
// Dr.Szumlanksi
// Skip List

import java.io.*;
import java.util.*;

class Node<T extends Comparable <T>>
{
	
	public T data;
	public int height;
	public ArrayList<Node<T>> references;
	
	// Makes new Node with Height _height and sets data && references to null
	Node(int _height){
	
		this.height= _height;

		this.data = null;
				
		this.references = new ArrayList<>();
		for(int i=0; i<height;i++)
			this.references.add(null);
		
		
	}
	
	// Makes new Node with Height _height and sets data to _data references to null
	Node(T _data, int _height){
		
		this.height=_height;

		this.data = _data;
				
		this.references = new ArrayList<>();
		for(int i=0; i<height;i++)
			this.references.add(null);
	
		
	}
	
	//returns data
	public T value(){
	
		return this.data;
		
	}
	
	//returns height
	public int height(){
	
		return this.height;
	
	}
	
	//returns next node on same level.
	public Node<T> next(int level){
			if(level < 0 || level > (this.height-1)){
					return null;
			}
			
			return this.references.get(level);
	}
	//Never used
	public void setNext(int level, Node<T> node){
		
			this.references.set(level,node);
		
	}
	// adds 1 to height and a null in references 
	public void grow(){
		
		this.references.add(null);
		this.height +=1;
	
	}
	
	// 50% chance to grow ; 1 if true 0 if false
	public int maybeGrow(){
		
		Random r = new Random();
		
		int coin = r.nextInt(2);
		
		if(coin==0){
			this.references.add(null);
			this.height +=1;
			return 1;
		}
	return 0;
	}
	
	// cuts height by 1, removes top reference.
	public void trim(int _height){
		
		while (this.height>_height){
			
			this.references.remove(this.height-1);
			this.height -=1;
			
		}
	
	}
	
	
}

public class SkipList<T extends Comparable <T>> 
{
	
	public int compareTo(T rhs) {
		
		// Put everything into a string form so it can be compared;
	String RHS = rhs.toString();
	String LHS = this.toString();
	boolean flag = false;
	
	//Char arrays are easier to work with.
	char[] chRHS = RHS.toCharArray();
	char[] chLHS = LHS.toCharArray();
	
	int least=0;
	
	// Find the shortest "word"
	if (chRHS.length > chLHS.length)
		least = chLHS.length;
	else if (chRHS.length < chLHS.length){
		least = chRHS.length;
	}
	else {
		least = chRHS.length;
		flag = true;
	}
	
	//Find the point where the words differ and see if which letter comes first.
	for (int i =0; i<least; i++)
	{
		if (chRHS[i]!=chLHS[i])
			return chLHS[i]-chRHS[i];
	}
	
	// if same length then return same
	if(flag){
		return 0;
	}
	else{
		//if no differences return longer word.
		return chLHS.length - chRHS.length;
	}
}
	
	Node<T> Head;
	int height;
	int size;
	
	//Constructor; height is 1 size is 0
	SkipList() {
		
		
		Head = new Node<>(1);
		this.height=1;
		this.size=0;
	
	}
	
	//Constructor V2.0 -- Height cannot be less than 1
	SkipList(int _height){
			
		if(_height<1)
			_height=1;
	
		Head = new Node<>(_height);
		this.height=(_height);
		this.size=0;
	}
	
	// Helper function for debugging
	public void print(){
		
	Node<T> current = this.Head;
	while (current!=null){
	for(Node<T> a : current.references){

		if(a!=null){
			System.out.print(a.value()+" ");
		}
		else{
			System.out.print("Null ");
		}
		
	}
		System.out.print("::"+ current.value());
		System.out.println("");
		if(current.next(0)!=null){
			System.out.println("--->");
			current=current.next(0);
		}
		else	
			break;
	}
	}
	
	public void insert(T data){
		
		Node<T> current = this.Head;
		int level = this.height()-1;
		ArrayList<Node<T>> previous = new ArrayList<>();
		for(int i=0; i<this.height();i++)
			previous.add(i,null);
		
		//make node first at max Head Height.
		Node<T> temp = new Node<T>(data, generateRandomHeight(this.height()));
		
		
		while (level>=0){
			//if null or data >= --> level-- (maybe store the level pointers)
			if (current.next(level) == null || current.next(level).value().compareTo(data)>=0){
				
					previous.set(level,current.next(level));
					
					// setting previous nodes to point to new inserted node.
					if(level<temp.height()){
						
							current.references.set(level,temp);
					
					}
					level--;
					
			}
			//if data < --> skip to next
			else if (current.next(level).value().compareTo(data)<0){
			
				current = current.next(level);
				
			}
		}
		
		// sets the temp nodes pointers to its next pointers.
		for(int i=0;i<temp.height();i++){

				temp.references.set(i,previous.get(i));

		}
		
		//remember to increase size of SkipList
		this.size+=1;
		//remember to increase height of all max nodes and Head node.
		
	
		if (getMaxHeight(this.size)>this.height()){
			this.grow();
		}
		
	}
	
	public void insert(T data, int _height){
		
		Node<T> current = this.Head;
		int level = this.height()-1;
		ArrayList<Node<T>> previous = new ArrayList<>();
		for(int i=0; i<this.height();i++)
			previous.add(i,null);
		
		//make node first at max Head Height.
		Node<T> temp = new Node<T>(data, _height);
		
		
		while (level>=0){
			//if null or data >= --> level-- (maybe store the level pointers)
			if (current.next(level) == null || current.next(level).value().compareTo(data)>=0){
				
					previous.set(level,current.next(level));
					
					// setting previous nodes to point to new inserted node.
					if(level<temp.height()){
						
							current.references.set(level,temp);
					
					}
					level--;
					
			}
			//if data < --> skip to next
			else if (current.next(level).value().compareTo(data)<0){
			
				current = current.next(level);
				
			}
		}
		
		// sets the temp nodes pointers to its next pointers.
		for(int i=0;i<temp.height();i++){
				
				temp.references.set(i,previous.get(i));

		}
		
		//remember to increase size of SkipList
		this.size+=1;
		//remember to increase height of all max nodes and Head node.
		
		
		if (getMaxHeight(this.size)>this.height()){
			this.grow();
		}
		
	}
	
	private void grow(){
		
			Node<T> previous = this.Head;
			int previousMax=this.height()-1;
			
			//increase skiplist height
			this.height+=1;
			
			//increase head node 
			previous.grow();
			
			//set next if there is max level node
			Node<T> current;
			if(previous.next(previousMax)!=null)
					current = previous.next(previousMax);
			else
				return;
			
			
			
			int newMax=this.height()-1;
			
			int flag=0;
			
			
			while (flag==0){
			
				if(current.maybeGrow()==1){
					
					previous.references.set(newMax,current);
					
					if(current.next(previousMax)!=null){

						previous=current;
						
						current=current.next(previousMax);
						
					
					}
					else
						flag=1;
				
				}
				else{
				
					if(current.next(previousMax)!=null){
						
						current=current.next(previousMax);
					
					}
					else
						flag=1;
					
				}
				
				
				
			}
		
	}
	
	// I wrote this function at 2 in the morning.
	// It deletes things.
	public void delete (T data){
		
		Node <T> current = this.Head;
		boolean deleted = false;
		int level = this.height()-1;
		ArrayList<Node<T>> prev = new ArrayList<>();
		for (int i =0;i<this.height();i++)
			prev.add(null);
		
		//Initial pass through stores references after node to be deleted
		while (level>=0){
				
				
				if (current.next(level)==null||current.next(level).value().compareTo(data)>=0){
					
					if(current.next(level)==null || current.next(level).next(level)==null){
						
					}
					else if(current.next(level).value().compareTo(data)==0){
						prev.set(level,current.next(level).next(level));
						deleted=true;
					}
					level--;
				}
				else if(current.next(level).value().compareTo(data)<0){
					current=current.next(level);
				}
				
				
				
		}
		int heightNode=0;
		if(current.next(level+1)!=null && current.next(level+1).value().compareTo(data)==0){
				deleted=true;
				heightNode = current.next(0).height();
		}
		
		level =this.height()-1;
		current=this.Head;
		
		// second pass through changes values of previous nodes if the deletion happened.
		while (level>=0&&deleted){
				
				
				if (current.next(level)==null||current.next(level).value().compareTo(data)>=0){
					if(level<heightNode){
						current.references.set(level,prev.get(level));
					}
					level--;
				}
				else if(current.next(level).value().compareTo(data)<0){
					current=current.next(level);
				}
				
				
				
		}
		
		// trim down skiplist if you break a log2 celieng(floor?)
		if(deleted == true){
			
				this.size -= 1;
				
				while(getMaxHeight(this.size())<this.height()){
					
					
					trim();
					
				}
			
		}
		
	}
	
	public void trim () {
			int flag=0;
			int level = this.height()-1;
			this.height-=1;
			
			Node<T> previous = this.Head;
			Node<T> current;
			if(previous.next(level)!=null){
				current = this.Head.next(level);
			}
			else{
					previous.height-=1;
					return;
			}
			
			
			while (flag==0){
				
				previous.height-=1;
				previous.references.remove(previous.height);
				previous=current;
				if(current.next(level)!=null){
					current=current.next(level);
				}
				else
					flag=1;
				
			}
		
		previous.height-=1;
		previous.references.remove(previous.height);
		
		
	}
	
	public boolean contains (T data){
		
			
			
			int level = this.height-1;
			
			Node <T> current = this.Head;
			
			while (level>=0){
				
				
				if (current.next(level)==null||current.next(level).value().compareTo(data)>0){
					level--;
				}
				else if(current.next(level).value().compareTo(data)<0){
					current=current.next(level);
				}
				else {
					return true;
				}
				
					
				
			}
			
			return false;
			
		
		
	}
	
	private static int getMaxHeight(int n) {
		
			int i=2,counter=1;
			
			while (n>i){
				
					i*=2;
					counter++;
			}
			
		return counter;
	}
	
	private static int generateRandomHeight (int maxHeight){
		
			Random r = new Random();
			
			int height=0;
			
			int coin=0;
			
			while (coin!=1){
				
				coin=r.nextInt(2);
				height++;
				
				if (height == maxHeight)
					break;
				
			}
		
		return height;
		
	}
	
	public int size(){
		
			return this.size;
	
	}
	
	public int height(){
		
			return this.height;
	
	}
	
	public Node<T> head(){
		
			return this.Head;
	
	}
	
	public static double difficultyRating(){
		return 4;
	}
	public static double hoursSpent(){
		return 25;
	}
	

	
}
