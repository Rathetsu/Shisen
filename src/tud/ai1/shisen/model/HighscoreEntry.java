package tud.ai1.shisen.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
  * Class representing a single high score entry.
  *
  * @author Ahmed Ezzat
  *
  */
public class HighscoreEntry implements Comparable<HighscoreEntry> {

	private LocalDateTime date;

	private double duration;

	private int score;

	private static final String separator = ";";

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

	public HighscoreEntry(LocalDateTime date, int score, double duration) {
		validate(date, score, duration);
		this.date = date;
		this.score = score;
		this.duration = duration;
	}

	public HighscoreEntry(String data) {
		String[] values = data.split(separator);
		this.date = LocalDateTime.parse(values[0], formatter);
		this.score = Integer.parseInt(values[1]);
		this.duration = Double.parseDouble(values[2]);
	}

	public void validate(LocalDateTime date, int score, double duration) {
		if (date == null || score < 0 || duration < 0) {
			throw new IllegalArgumentException("Invalid arguments");
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		HighscoreEntry other = (HighscoreEntry) obj;
		return score == other.score && Double.compare(other.duration, duration) == 0 && date.equals(other.date);
	}

	public String getDate() {
		return String.format("%02d.%02d. %02d:%02d", date.getDayOfMonth(), date.getMonthValue(), date.getHour(),
				date.getMinute());
	}

	public int getScore() {
		return score;
	}

	public double getDuration() {
		return duration;
	}

	private String dateToSaveFormat() {
		return String.format("%02d.%02d.%02d %02d:%02d", date.getDayOfMonth(), date.getMonthValue(), date.getYear(),
				date.getHour(), date.getMinute());
	}

	@Override
	public int compareTo(HighscoreEntry other) {
		int scoreComparison = Integer.compare(other.score, this.score);
		if(scoreComparison != 0) {
			return scoreComparison;
		}
		int durationComparison = Double.compare(this.duration, other.duration);
		if(durationComparison != 0) {
			return durationComparison;
		}
		return this.date.compareTo(other.date);
	}

	@Override
	public String toString() {
		return dateToSaveFormat() + separator + score + separator + duration;
	}
}
