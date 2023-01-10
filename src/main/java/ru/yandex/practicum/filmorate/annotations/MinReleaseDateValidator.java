package ru.yandex.practicum.filmorate.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

import static ru.yandex.practicum.filmorate.utilities.Constants.MIN_RELEASE_DATE;

public class MinReleaseDateValidator implements ConstraintValidator<MinReleaseDateValidation, LocalDate>
{
    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext cxt) {
        return !date.isBefore(MIN_RELEASE_DATE);
    }
}
