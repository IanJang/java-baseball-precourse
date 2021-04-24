package baseball;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Baseball {
	private static final int MIN_NUMBER = 1;
	private static final int MAX_NUMBER = 9;
	private static final int SIZE_OF_NUMBERS = 3;
	List<Integer> answerNumbers;

	public void makeAnswerNumbers() {
		answerNumbers = new ArrayList<>();
		while (answerNumbers.size() < SIZE_OF_NUMBERS) {
			makeUniqueAnswerNumber();
		}
	}

	private void makeUniqueAnswerNumber() {
		Random rand = new Random();
		int number = rand.nextInt(MAX_NUMBER) + MIN_NUMBER;
		if (answerNumbers.contains(number)) {
			return;
		}
		answerNumbers.add(number);
	}

	void setAnswerNumbers(List<Integer> value) {
		answerNumbers = value;
	}

	public boolean checkAnswer(List<Integer> numbers) {
		BallCount ballCount = new BallCount();
		for (int i = 0; i < numbers.size(); i++) {
			int number = numbers.get(i);
			ballCount.add(getBallCountFromNumber(number, i));
		}
		printBallCount(ballCount);
		return ballCount.getStrike() == SIZE_OF_NUMBERS;
	}

	private BallCount getBallCountFromNumber(int number, int idx) {
		BallCount ballCount = new BallCount();
		if (answerNumbers.contains(number) && answerNumbers.get(idx) == number) {
			ballCount.setStrike(ballCount.getStrike() + 1);
		} else if (answerNumbers.contains(number) && answerNumbers.get(idx) != number) {
			ballCount.setBall(ballCount.getBall() + 1);
		}
		return ballCount;
	}

	private void printBallCount(BallCount ballCount) {
		int strike = ballCount.getStrike();
		int ball = ballCount.getBall();
		String ballCountString = "낫싱";
		if (strike > 0 && ball > 0) {
			ballCountString = String.format("%d 스트라이크 %d볼", strike, ball);
		} else if (strike > 0 && ball == 0) {
			ballCountString = String.format("%d 스트라이크", strike);
		} else if (strike == 0 && ball > 0) {
			ballCountString = String.format("%d볼", ball);
		}
		System.out.println(ballCountString);
	}
}
