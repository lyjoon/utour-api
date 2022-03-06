package com.utour.util;

import lombok.Getter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Objects;
import java.util.Optional;

/**
 * @param <T>
 */
public final class MapObjects<T extends java.util.Map> {

	@Getter
	private final T value;

	/**
	 * construct
	 * @param value
	 */
	private MapObjects(T value){ this.value = value;}

	/**
	 * create Map
	 * @param key
	 * @param value
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K, V> java.util.Map<K,V> of(K key, V value) {
		return new java.util.LinkedHashMap<K, V>(){{put(key, value);}};
	}

	/**
	 * create MapObject
	 * @param value
	 * @param <T>
	 * @return
	 */
	public static <T extends java.util.Map> MapObjects of(T value){
		return new MapObjects(value);
	}

	/**
	 * @param key
	 * @param value
	 * @param <T>
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <T extends java.util.Map, K, V> MapObjects createOf(K key, V value){
		return new MapObjects(of(key, value));
	}

	/**
	 * this.value.put(k, v)
	 * @param key
	 * @param value
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public <K, V> MapObjects put(K key, V value){
		this.value.put(key, value);
		return this;
	}

	/**
	 * @param key
	 * @return
	 */
	public Object get(String key){
		return Optional.ofNullable(value).map(v -> v.get(key)).orElse(null);
	}


	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String string(String key, String defaultValue){
		return Optional.ofNullable(this.get(key)).map(e -> e.toString()).orElse(defaultValue);
	}

	/**
	 * @param key
	 * @return
	 */
	public String string(String key){
		return this.string(key, null);
	}

	/**
	 * @param key
	 * @return
	 */
	public Number number(String key){
		return Optional.ofNullable(value.get(key))
			.map(v -> {
				if(v instanceof Number) return (Number) v;
				else if(v instanceof String) {
					try {
						return NumberFormat.getInstance().parse((String)v);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return null;
				} else return null;
			}).orElse(null);
	}

	/**
	 * @param key
	 * @return
	 */
	public Boolean aBoolean(String key, Boolean defaultBoolean){
		return Optional.ofNullable(value.get(key)).map(bool -> {
			if(Objects.nonNull(bool)) {
				if(bool instanceof Boolean) return (Boolean) bool;
				else if (bool instanceof String) return Boolean.valueOf((String) bool);
				else if (bool instanceof Number) return ((Number) bool).intValue() > 0;
				else return defaultBoolean;
			}
			else return defaultBoolean;
		}).orElse(defaultBoolean);
	}

	/**
	 * @param key
	 * @return
	 */
	public Boolean aBoolean(String key){
		return aBoolean(key, null);
	}

	/**
	 * @param key
	 * @return
	 */
	public Integer integer(String key){
		return integer(key, null);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public Integer integer(String key, Integer defaultValue){
		return Optional.ofNullable(number(key)).map(v -> v.intValue()).orElse(defaultValue);
	}
}
