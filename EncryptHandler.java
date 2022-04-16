package returners;

public class EncryptHandler {

	private int key;
	private int spacing;
	private int randomMin;
	private int randomMax;
	private int definedLength;
	private int startAt;
	private int secretKey;
	private MathHandler math = new MathHandler();
	
	public EncryptHandler() {
		setKey(30);
		setSpacing(10);
		setRandomMin(33);
		setRandomMax(127);
		setDefinedLength(1000);
		setStartAt(222);
		setSecretKey(getDefinedLength() - getStartAt());
	}
	
	public EncryptHandler(int key, int spacing, int randomMin, int randomMax, int definedLength, int startAt) {
		setKey(key);
		setSpacing(spacing);
		setRandomMin(randomMin);
		setRandomMax(randomMax);
		setDefinedLength(definedLength);
		setStartAt(startAt);
		setSecretKey(getDefinedLength() - getStartAt());
	}

	protected int getKey() {
		return key;
	}

	protected void setKey(int key) {
		this.key = key;
	}

	protected int getSpacing() {
		return spacing;
	}

	protected void setSpacing(int spacing) {
		this.spacing = spacing;
	}

	protected int getRandomMin() {
		return randomMin;
	}

	protected void setRandomMin(int randomMin) {
		this.randomMin = randomMin;
	}

	protected int getRandomMax() {
		return randomMax;
	}

	protected void setRandomMax(int randomMax) {
		this.randomMax = randomMax;
	}

	protected int getDefinedLength() {
		return definedLength;
	}

	protected void setDefinedLength(int definedLength) {
		this.definedLength = definedLength;
	}

	protected int getStartAt() {
		return startAt;
	}

	protected void setStartAt(int startAt) {
		this.startAt = startAt;
	}

	protected int getSecretKey() {
		return secretKey;
	}

	protected void setSecretKey(int secretKey) {
		this.secretKey = secretKey;
	}

	protected MathHandler getMath() {
		return math;
	}

	protected void setMath(MathHandler math) {
		this.math = math;
	}

	private char getRandomChar() {
		char randomChar = (char) ('a' + math.getRandomNumber(this.randomMin, this.randomMax));
		return randomChar;
	}

	private String generateString(int count) {
		String generatedText = "";
		for (int i = 0; i < count; i++)
			generatedText += getRandomChar();
		return generatedText;
	}

	private char encryptChar(char character) {
		char encryptedChar = (char) (character + this.key);
		return encryptedChar;
	}

	private char decryptChar(char character) {
		char decryptedChar = (char) (character - this.key);
		return decryptedChar;
	}

	private String encryptString(String text) {
		String encText = "";
		char[] textChars = text.toCharArray();
		for (int i = 0; i < textChars.length; i++) {
			textChars[i] = encryptChar(textChars[i]);
			encText += textChars[i];
			for (int j = 0; j < this.spacing; j++) {
				encText += getRandomChar();
			}
		}
		return encText;
	}

	private String insertSecretEnd(String text, int end) {
		StringBuilder sb = new StringBuilder(text);
		char[] endAt = new char[math.readDigits(end)];
		for (int i = 0; i < math.readDigits(end); i++) {
			endAt[i] = (String.valueOf(end)).charAt(i);
			sb.setCharAt((this.secretKey + i), encryptChar(endAt[i]));
		}
		String doneText = sb.toString();
		return doneText;
	}

	private int readSecretEnd(String text) {
		String end = "";
		for (int i = 0; i < math.readDigits(this.startAt); i++) {
			end += decryptChar(text.charAt(secretKey + i));
		}
		int endAt = Integer.valueOf(end);
		return endAt;
	}

	private String decryptString(String text, int start, int end) {
		String decText = "";
		char[] textChars = text.toCharArray();
		for (int i = start; i < textChars.length; i++) {
			textChars[i] -= this.key;
			decText += textChars[i];
			i += this.spacing;
			if (i == end)
				break;
		}
		return decText;
	}
	
	private String reverseString(String text) {
		StringBuilder sb = new StringBuilder(text);
		sb.reverse();
		return sb.toString();
	}
	
	private String invertStringContents(String text, boolean inRe) {
		char[] textChars = text.toCharArray();
		String partOne = "";
		String partTwo = "";
		if((textChars.length%2) == 0) {
			for(int i=0; i<(textChars.length/2); i++) {
				partOne += textChars[(textChars.length/2) + i];
				partTwo += textChars[i];
			}
		} else {
			if(inRe) {
				for(int i=0; i<((textChars.length-1)/2); i++) {
					partOne += textChars[((textChars.length-1)/2) + i];
					partTwo += textChars[i];
				}
				partOne += textChars[textChars.length-1];
			} else {
				for(int i=0; i<((textChars.length+1)/2); i++) {
					if(i < ((textChars.length-1)/2)-1)
						partOne += textChars[((textChars.length+1)/2) + i];
					partTwo += textChars[i];
				}
				partOne += textChars[textChars.length-1];
			}
		}
		String invertedText = partOne + partTwo;
		return invertedText;
	}

	protected String encrypt(String text) {
		String encText = "";

		encText += generateString(this.startAt);
		encText += encryptString(invertStringContents(reverseString(text), true));
		int endAt = (encText.length() - 1);
		encText += generateString(this.definedLength - endAt);
		String tempText = insertSecretEnd(encText, endAt);
		encText = tempText;
		
		return encText;
	}

	protected String decrypt(String text) {
		String decText = "";
		decText = decryptString(text, this.startAt, readSecretEnd(text));
		return reverseString(invertStringContents(decText, false));
	}
	
	@Override
	public String toString() {
		return "Please use the encrypt() and decrypt() methods.";
	}

	private class MathHandler {
		
		public int getRandomNumber(int min, int max) {
			int randomNumber = (int) (Math.random() * max + min);
			return randomNumber;
		}
		
		public int readDigits(int number) {
			int digits = (int)(Math.floor(Math.log10(Math.abs(number))) + 1);
			return digits;
		}
		
	}


}