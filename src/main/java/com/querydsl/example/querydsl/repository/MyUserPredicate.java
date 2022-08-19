package com.querydsl.example.querydsl.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.example.querydsl.model.MyUser;

public class MyUserPredicate {
    private SearchCriteria searchCriteria;

    public MyUserPredicate(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public BooleanExpression getPredicate() {
        var entityPath = new PathBuilder<>(MyUser.class, "myUser");
        if (isNumeric(searchCriteria.getValue().toString())) {
            var path = entityPath.getNumber(searchCriteria.getKey(), Integer.class);
            var value = Integer.parseInt(searchCriteria.getValue().toString());
            switch (searchCriteria.getOperation()) {
                case ":":
                    return path.eq(value);
                case ">":
                    return path.goe(value);
                case "<":
                    return path.loe(value);
            }
        } else {
            var path = entityPath.getString(searchCriteria.getKey());
            if (searchCriteria.getOperation().equalsIgnoreCase(":")) {
                return path.containsIgnoreCase(searchCriteria.getValue().toString());
            }
        }
        return null;
    }

    private static boolean isNumeric(final String str) {
        try {
            Integer.parseInt(str);
        } catch (final NumberFormatException e) {
            return false;
        }
        return true;
    }
}
