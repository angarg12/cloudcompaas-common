package org.cloudcompaas.common.util;

/**
 * @author angarg12
 *
 */
public class StringComparator implements Comparator {

	public boolean compare(Object element1, Object element2,
			ComparisonOperation operation) {
		
		if(operation == ComparisonOperation.EQUAL){
			if(element1.toString().compareTo(element2.toString()) == 0) return true;
		}
		if(operation == ComparisonOperation.GREATER){
			if(element1.toString().compareTo(element2.toString()) > 0) return true;
		}
		if(operation == ComparisonOperation.GREATER_OR_EQUAL){
			if(element1.toString().compareTo(element2.toString()) >= 0) return true;
		}
		if(operation == ComparisonOperation.LESS){
			if(element1.toString().compareTo(element2.toString()) < 0) return true;
		}
		if(operation == ComparisonOperation.LESS_OR_EQUAL){
			if(element1.toString().compareTo(element2.toString()) <= 0) return true;
		}
		
		return false;
	}
}
