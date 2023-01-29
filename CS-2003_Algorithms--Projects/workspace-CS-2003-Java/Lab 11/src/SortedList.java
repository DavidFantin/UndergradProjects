//Name: David Fantin
//ID: 1525813
//Lab Section: 2000-03
//Lecture Section: 2003-02
//Date: 3/27/19
//Assignment: Lab 11

public class SortedList<E extends Comparable<? super E>> implements SortedListInterface<E> {

	ListArrayListBased<E> list;

	public SortedList() {
		list = new ListArrayListBased<E>();
	}

	public SortedList(E index) {
		list = new ListArrayListBased<E>();
		sortedAdd(index);
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public int size() {
		return list.size();
	}

	public void removeAll() {
		list.removeAll();
	}

	public E get(int index) throws ListIndexOutOfBoundsException {
		return list.get(index);
	}

	public void sortedAdd(E newItem) throws ListException {
		int newPosition = locateIndex(newItem);
		list.add(newPosition, newItem);
	}

	public void sortedRemove(E anItem) {
		int position = locateIndex(anItem);
		if(anItem.compareTo(get(position))==0)
			list.remove(position);
		else
			throw new ListException("sortedRemove() failed");
	}

	public int locateIndex(E anItem) {
		int p = 1;
		int length = size();
		int mid = 1;
		while (p <= length) {
			mid = (length + p) / 2;
			if (p == length) {
				if (anItem.compareTo(get(p)) < 0) {
					mid = mid + 1;
					return mid;
				} else
					return mid;
			}

			else if (anItem.compareTo(get(mid)) == 0) {
				return mid;
			}

			else if (anItem.compareTo(get(mid)) > 0) {
				length = mid - 1;
			}

			else {
				p = mid + 1;
			}
		}
		return mid;
	}

	public void display() {
		for (E elm : list)
			System.out.print(elm + " ");
		System.out.println();
	}
} // end class