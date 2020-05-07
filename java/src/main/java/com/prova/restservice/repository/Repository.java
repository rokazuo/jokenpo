package com.prova.restservice.repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.prova.restservice.model.Gamer;
import com.prova.restservice.model.MoveType;

public class Repository {

	private final String REPOSITORY_PATH = "src/main/java/repository.txt";

	public Gamer findById(String name) {
		return this.findAllIfGamerIsNullOrGamerInList(null).stream()
				.filter(gamerElement -> gamerElement.getName().equals(name)).findFirst().orElse(null);
	}

	public Gamer save(Gamer gamer) {
		return save(gamer, false);
	}

	public Gamer deleteByName(String name) {
		return save(new Gamer(name, MoveType.Invalid), true);
	}

	public List<Gamer> findAll() {
		return this.findAllIfGamerIsNullOrGamerInList(null);
	}

	public void deleteAll() {
		FileWriter fileWritter = null;
		PrintWriter printWritter = null;
		try {
			File file = new File(REPOSITORY_PATH);
			fileWritter = new FileWriter(file, false);
			printWritter = new PrintWriter(fileWritter);
			printWritter.write("");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fileWritter != null) {
					fileWritter.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private Gamer save(Gamer gamer, boolean shouldRemove) {
		List<Gamer> gamerList = findAllIfGamerIsNullOrGamerInList(gamer);
		Gamer gamerFound = gamer;
		boolean append = false;
		if (gamerList == null) {
			if (shouldRemove) {
				gamerFound = null;
			} else {
				gamerList = new ArrayList<>();
				gamerList.add(gamer);
				append = true;
			}
		} else {
			gamerFound = gamerList.stream().filter(gamerElement -> gamerElement.getName().equals(gamer.getName()))
					.findFirst().orElse(null);
			if (gamerFound != null) {
				if (shouldRemove) {
					gamerList.remove(gamerFound);
				} else {
					if (!gamerFound.getMove().equals(gamer.getMove())) {
						gamerFound.setMove(gamer.getMove());
					}
				}
			}
		}
		if (gamerFound != null) {
			save(gamerList, append);
		}
		return gamerFound;
	}

	private void save(List<Gamer> gamerList, boolean append) {
		FileWriter fileWritter = null;
		PrintWriter printWritter = null;
		try {
			File file = new File(REPOSITORY_PATH);
			fileWritter = new FileWriter(file, append);
			printWritter = new PrintWriter(fileWritter);
			for (Gamer gamerElement : gamerList) {
				printWritter.write(gamerElement.getName() + ";" + gamerElement.getMove().toString());
				printWritter.println();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fileWritter != null) {
					fileWritter.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private List<Gamer> findAllIfGamerIsNullOrGamerInList(Gamer gamer) {
		List<Gamer> gamerList = new ArrayList<>();
		InputStream inputStream = null;
		BufferedReader bufferedReader = null;
		boolean wasFoundGamer = false;
		try {
			File file = new File(REPOSITORY_PATH);
			inputStream = new FileInputStream(file);
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				if (!"".equals(line)) {
					String[] lineSplitted = line.split(";");
					gamerList.add(new Gamer(lineSplitted[0], MoveType.valueOf(lineSplitted[1])));
					if (gamer != null && lineSplitted[0].equals(gamer.getName())) {
						wasFoundGamer = true;
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!wasFoundGamer && gamer != null) {
			gamerList = null;
		}
		return gamerList;
	}

}