package ru.yandex.practicum.filmorate.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Duration;

public class PositiveDurationValidator implements ConstraintValidator<PositiveDurationValidation, Duration>
{
    @Override
    public boolean isValid(Duration duration, ConstraintValidatorContext cxt) {
        return !duration.isNegative();
    }
}
