## Split up the array of whole names into an array of first/last names

```java
List<Object[]> splitUpNames = Arrays.asList("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long").stream()
        .map(name -> name.split(" ", 2))
        .collect(Collectors.toList());
```

## Use JdbcTemplate's batchUpdate operation to bulk load data

```java
jdbcTemplate.batchUpdate("INSERT INTO customers (first_name, last_name) VALUES (?, ?)", splitUpNames);
```

## Use JdbcTempate's query to get data

```java
jdbcTemplate.query(
        "SELECT id, first_name, last_name FROM customers WHERE first_name=?",
        new Object[] {"Josh"},
        (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"))
).forEach(customer -> log.info(customer.toString()));

```