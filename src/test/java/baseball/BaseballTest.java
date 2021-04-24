package baseball;

import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class BaseballTest {
	private final PrintStream standardOut = System.out;
	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

	@BeforeEach
	public void setUp() {
		System.setOut(new PrintStream(outputStreamCaptor));
	}

	@AfterEach
	public void tearDown() {
		System.setOut(standardOut);
	}

	@Test
	void makeAnswerNumbers() {
		Baseball baseball = new Baseball();
		baseball.makeAnswerNumbers();
		List<Integer> answerNumbers = baseball.answerNumbers;
		assertThat(answerNumbers).hasSize(3);

		HashSet<Integer> uniqueSet = new HashSet<>(answerNumbers);
		assertThat(uniqueSet).hasSize(3);

		for(Integer number: answerNumbers) {
			assertThat(number).isIn(1, 2, 3, 4, 5, 6, 7, 8, 9);
		}
	}

	@ParameterizedTest
	@CsvSource(value={
		"1,2,3:1,2,3:3 스트라이크:true",
		"1,2,3:1,3,2:1 스트라이크 2볼:false",
		"1,2,3:2,1,3:1 스트라이크 2볼:false",
		"1,2,3:2,3,1:3볼:false",
		"1,2,3:3,1,2:3볼:false",
		"1,2,3:3,2,1:1 스트라이크 2볼:false",
		"1,2,3:4,5,6:낫싱:false"
	}, delimiter=':')
	void checkAnswer(String answer, String input, String message, Boolean isCorrectAnswer) {
		Baseball baseball = new Baseball();
		String[] answerNumberStrings = answer.split(",");
		List<Integer> answerNumberList = new ArrayList<>();
		for (String numberString : answerNumberStrings) {
			answerNumberList.add(Integer.parseInt(numberString));
		}
		baseball.setAnswerNumbers(answerNumberList);

		String[] inputNumberStrings = input.split(",");
		List<Integer> inputNumberList = new ArrayList<>();
		for (String numberString:inputNumberStrings) {
			inputNumberList.add(Integer.parseInt(numberString));
		}
		assertThat(baseball.checkAnswer(inputNumberList)).isEqualTo(isCorrectAnswer);
		assertThat(message).isEqualTo(outputStreamCaptor.toString().trim());
	}
}
