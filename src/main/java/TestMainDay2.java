public class TestMainDay2 {

	public static void main(String[] args) {
		String intCodeInput = "1,0,0,3,1,1,2,3,1,3,4,3,1,5,0,3,2,10,1,19,1,5,19,23,1,23,5,27,1,27,13,31,1,31,5,35,1,9,35,39,2,13,39,43,1,43,10,47,1,47,13,51,2,10,51,55,1,55,5,59,1,59,5,63,1,63,13,67,1,13,67,71,1,71,10,75,1,6,75,79,1,6,79,83,2,10,83,87,1,87,5,91,1,5,91,95,2,95,10,99,1,9,99,103,1,103,13,107,2,10,107,111,2,13,111,115,1,6,115,119,1,119,10,123,2,9,123,127,2,127,9,131,1,131,10,135,1,135,2,139,1,10,139,0,99,2,0,14,0";
		String sampleCodeInput = "1,9,10,3,2,3,11,0,99,30,40,50";
		String[] array = intCodeInput.split(",");
		array[1] = "12";
		array[2] = "2";

		for (int noun = 0; noun < 100; noun++) {
			for (int verb = 0; verb < 100; verb++) {
				String[] inputArray = array.clone();
				inputArray[1] = Integer.toString(noun);
				inputArray[2] = Integer.toString(verb);
				int result = runIntCodeProgram(inputArray);
				
				System.out.println("Noun= " + noun + " Verb= " + verb + " res= " + result);
				
				if(result == 19690720) {
					System.out.println("FOUND!! Noun= " + noun + " Verb= " + verb + " res= " + result);
					return; 
				}
			}
		}
	}

	private static int runIntCodeProgram(String[] array) {
		int idxNextElement = 0;
		int nextElem = Integer.parseInt(array[idxNextElement]);
		int count = 1;

		while (nextElem != 99 && count < 1000) {
			switch (nextElem) {
			case 1:
				int el1 = Integer.parseInt(array[idxNextElement + 1]);
				int el2 = Integer.parseInt(array[idxNextElement + 2]);
				int el3 = Integer.parseInt(array[idxNextElement + 3]);
				Integer sum = Integer.parseInt(array[el1]) + Integer.parseInt(array[el2]);
				array[el3] = sum.toString();
				break;
			case 2:
				el1 = Integer.parseInt(array[idxNextElement + 1]);
				el2 = Integer.parseInt(array[idxNextElement + 2]);
				el3 = Integer.parseInt(array[idxNextElement + 3]);
				Integer mul = Integer.parseInt(array[el1]) * Integer.parseInt(array[el2]);
				array[el3] = mul.toString();
				break;
			default:
				System.out.println("Something went wrong!");
				System.out.println("A= " + array);
				System.out.println("nextElem= " + nextElem);
				System.out.println("idxNextElement= " + idxNextElement);
				System.out.println("#iter= " + count);
				return -1;
			}
			count++;
			idxNextElement += 4;
			nextElem = Integer.parseInt(array[idxNextElement]);
		}

		System.out.println("nextElem= " + nextElem);
		System.out.println("idxNextElement= " + idxNextElement);
		System.out.println("#iter= " + count);
		System.out.print("Array[0]= " + array[0]);

		return Integer.parseInt(array[0]);
	}
}

