package com.example.covid19.model;

import com.google.gson.annotations.SerializedName;

public class CaseCovid {

	@SerializedName("continent")
	private String continent;

	@SerializedName("country")
	private String country;

	@SerializedName("recoveredPerOneMillion")
	private double recoveredPerOneMillion;

	@SerializedName("cases")
	private int cases;

	@SerializedName("critical")
	private int critical;

	@SerializedName("oneCasePerPeople")
	private int oneCasePerPeople;

	@SerializedName("active")
	private int active;

	@SerializedName("testsPerOneMillion")
	private int testsPerOneMillion;

	@SerializedName("population")
	private int population;

	@SerializedName("oneDeathPerPeople")
	private int oneDeathPerPeople;

	@SerializedName("recovered")
	private int recovered;

	@SerializedName("oneTestPerPeople")
	private int oneTestPerPeople;

	@SerializedName("tests")
	private int tests;

	@SerializedName("criticalPerOneMillion")
	private double criticalPerOneMillion;

	@SerializedName("deathsPerOneMillion")
	private int deathsPerOneMillion;

	@SerializedName("todayRecovered")
	private int todayRecovered;

	@SerializedName("casesPerOneMillion")
	private int casesPerOneMillion;

	@SerializedName("countryInfo")
	private CountryInfo countryInfo;

	@SerializedName("updated")
	private long updated;

	@SerializedName("deaths")
	private int deaths;

	@SerializedName("activePerOneMillion")
	private double activePerOneMillion;

	@SerializedName("todayCases")
	private int todayCases;

	@SerializedName("todayDeaths")
	private int todayDeaths;

	public void setContinent(String continent){
		this.continent = continent;
	}

	public String getContinent(){
		return continent;
	}

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setRecoveredPerOneMillion(double recoveredPerOneMillion){
		this.recoveredPerOneMillion = recoveredPerOneMillion;
	}

	public double getRecoveredPerOneMillion(){
		return recoveredPerOneMillion;
	}

	public void setCases(int cases){
		this.cases = cases;
	}

	public int getCases(){
		return cases;
	}

	public void setCritical(int critical){
		this.critical = critical;
	}

	public int getCritical(){
		return critical;
	}

	public void setOneCasePerPeople(int oneCasePerPeople){
		this.oneCasePerPeople = oneCasePerPeople;
	}

	public int getOneCasePerPeople(){
		return oneCasePerPeople;
	}

	public void setActive(int active){
		this.active = active;
	}

	public int getActive(){
		return active;
	}

	public void setTestsPerOneMillion(int testsPerOneMillion){
		this.testsPerOneMillion = testsPerOneMillion;
	}

	public int getTestsPerOneMillion(){
		return testsPerOneMillion;
	}

	public void setPopulation(int population){
		this.population = population;
	}

	public int getPopulation(){
		return population;
	}

	public void setOneDeathPerPeople(int oneDeathPerPeople){
		this.oneDeathPerPeople = oneDeathPerPeople;
	}

	public int getOneDeathPerPeople(){
		return oneDeathPerPeople;
	}

	public void setRecovered(int recovered){
		this.recovered = recovered;
	}

	public int getRecovered(){
		return recovered;
	}

	public void setOneTestPerPeople(int oneTestPerPeople){
		this.oneTestPerPeople = oneTestPerPeople;
	}

	public int getOneTestPerPeople(){
		return oneTestPerPeople;
	}

	public void setTests(int tests){
		this.tests = tests;
	}

	public int getTests(){
		return tests;
	}

	public void setCriticalPerOneMillion(double criticalPerOneMillion){
		this.criticalPerOneMillion = criticalPerOneMillion;
	}

	public double getCriticalPerOneMillion(){
		return criticalPerOneMillion;
	}

	public void setDeathsPerOneMillion(int deathsPerOneMillion){
		this.deathsPerOneMillion = deathsPerOneMillion;
	}

	public int getDeathsPerOneMillion(){
		return deathsPerOneMillion;
	}

	public void setTodayRecovered(int todayRecovered){
		this.todayRecovered = todayRecovered;
	}

	public int getTodayRecovered(){
		return todayRecovered;
	}

	public void setCasesPerOneMillion(int casesPerOneMillion){
		this.casesPerOneMillion = casesPerOneMillion;
	}

	public int getCasesPerOneMillion(){
		return casesPerOneMillion;
	}

	public void setCountryInfo(CountryInfo countryInfo){
		this.countryInfo = countryInfo;
	}

	public CountryInfo getCountryInfo(){
		return countryInfo;
	}

	public void setUpdated(long updated){
		this.updated = updated;
	}

	public long getUpdated(){
		return updated;
	}

	public void setDeaths(int deaths){
		this.deaths = deaths;
	}

	public int getDeaths(){
		return deaths;
	}

	public void setActivePerOneMillion(double activePerOneMillion){
		this.activePerOneMillion = activePerOneMillion;
	}

	public double getActivePerOneMillion(){
		return activePerOneMillion;
	}

	public void setTodayCases(int todayCases){
		this.todayCases = todayCases;
	}

	public int getTodayCases(){
		return todayCases;
	}

	public void setTodayDeaths(int todayDeaths){
		this.todayDeaths = todayDeaths;
	}

	public int getTodayDeaths(){
		return todayDeaths;
	}
}
