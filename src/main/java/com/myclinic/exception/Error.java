package com.myclinic.exception;


record Error(
        String field,
        String input,
        String errorMessage
) {

}
