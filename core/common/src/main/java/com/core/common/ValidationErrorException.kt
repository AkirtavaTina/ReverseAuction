package com.core.common

class ValidationErrorException(val validationErrors: Map<String, List<String>>) : Exception("Problem with validating data.")
