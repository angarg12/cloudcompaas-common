package org.cloudcompaas.common.util;

import es.upv.grycap.cloudcompaas.BoundaryType;
import es.upv.grycap.cloudcompaas.ExactType;
import es.upv.grycap.cloudcompaas.RangeType;
import es.upv.grycap.cloudcompaas.RangeValueType;

/**
 * @author angarg12
 *
 */
public class Range {
	private RangeValueType range;
	
	public Range(RangeValueType object){
		range = object;
	}
	
	/*
	public Integer[] getExact(){
		if(range.getExactArray() == null) return null;
		Integer[] result = new Integer[range.getExactArray().length];
		ExactType[] exacts = range.getExactArray();
		for(int i = 0; i < exacts.length; i++){
			result[i] = exacts[i].getBigIntegerValue().intValue();
		}
		return result;
	}
	*/
	
	/*
	public Integer[] getMinimum(){
		Collection<Integer> mins = new Vector<Integer>();
		
		ExactType[] exacts = range.getExactArray();
		if(exacts != null){
			for(int i = 0; i < exacts.length; i++){
				if(exacts[i].getEpsilon() != null){
					mins.add(exacts[i].getBigIntegerValue().subtract(exacts[i].getEpsilon()).intValue());
				}
			}
		}
		
		BoundaryType lbr = range.getLowerBoundedRange();
		if(lbr != null){
			if(lbr.getExclusiveBound() == true){
				mins.add(lbr.getBigIntegerValue().add(new BigInteger("1")).intValue());
			}else{
				mins.add(lbr.getBigIntegerValue().intValue());
			}
		}
		
		RangeType[] ranges = range.getRangeArray();
		if(ranges != null){
			for(int i = 0; i < ranges.length; i++){
				if(ranges[i].getLowerBound().getExclusiveBound() == true){
					mins.add(ranges[i].getLowerBound().getBigIntegerValue().add(new BigInteger("1")).intValue());
				}else{
					mins.add(ranges[i].getLowerBound().getBigIntegerValue().intValue());
				}
			}
		}

		return (Integer[]) mins.toArray();
	}
	*/
	
	/*
	 public Integer[] getMaximum(){
		Collection<Integer> maxs = new Vector<Integer>();
		
		ExactType[] exacts = range.getExactArray();
		if(exacts != null){
			for(int i = 0; i < exacts.length; i++){
				if(exacts[i].getEpsilon() != null){
					maxs.add(exacts[i].getBigIntegerValue().add(exacts[i].getEpsilon()).intValue());
				}
			}
		}
		
		BoundaryType ubr = range.getUpperBoundedRange();
		if(ubr != null){
			if(ubr.getExclusiveBound() == true){
				maxs.add(ubr.getBigIntegerValue().subtract(new BigInteger("1")).intValue());
			}else{
				maxs.add(ubr.getBigIntegerValue().intValue());
			}
		}
		
		RangeType[] ranges = range.getRangeArray();
		if(ranges != null){
			for(int i = 0; i < ranges.length; i++){
				if(ranges[i].getUpperBound().getExclusiveBound() == true){
					maxs.add(ranges[i].getLowerBound().getBigIntegerValue().subtract(new BigInteger("1")).intValue());
				}else{
					maxs.add(ranges[i].getLowerBound().getBigIntegerValue().intValue());
				}
			}
		}

		return (Integer[]) maxs.toArray();
	}
	*/
	
	public boolean isInRange(int value) throws Exception {
		ExactType[] exacts = range.getExactArray();
		BoundaryType lbr = range.getLowerBoundedRange();
		BoundaryType ubr = range.getUpperBoundedRange();
		RangeType[] ranges = range.getRangeArray();
		
		if(exacts == null && lbr == null && ubr == null && ranges == null){
			throw new Exception("No value has been defined for Range.");
		}
		
		if(exacts != null){
			for(int i = 0; i < exacts.length; i++){
				if(exacts[i].getBigIntegerValue().intValue() == value) return true;
				if(exacts[i].isSetEpsilon() == true){
					long min = exacts[i].getBigIntegerValue().intValue()-exacts[i].getEpsilon();
					long max = exacts[i].getBigIntegerValue().intValue()+exacts[i].getEpsilon();
					if(value >= min && value <= max){
						return true;
					}
				}
			}
		}
		
		if(lbr != null){
			int min = lbr.getBigIntegerValue().intValue();
			if(lbr.getExclusiveBound() == true){
				min++;
			}
			if(value >= min){
				return true;
			}
		}
		
		if(ubr != null){
			int max = ubr.getBigIntegerValue().intValue();
			if(ubr.getExclusiveBound() == true){
				max--;
			}
			if(value <= max){
				return true;
			}
		}
		
		if(ranges != null){
			for(int i = 0; i < ranges.length; i++){
				int min = ranges[i].getLowerBound().getBigIntegerValue().intValue();
				int max = ranges[i].getUpperBound().getBigIntegerValue().intValue();
				if(ranges[i].getLowerBound().getExclusiveBound() == true){
					min++;
				}
				if(ranges[i].getUpperBound().getExclusiveBound() == true){
					max--;
				}
				if(max > min){
					throw new Exception("PhysicalResource value malformed: upper and lower bounds are exclusive.");
				}
				if(value >= min && value <= max){
					return true;
				}
			}
		}

		return false;
	}
}
