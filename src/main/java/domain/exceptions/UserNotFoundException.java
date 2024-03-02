package domain.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Getter
@Setter
@ToString(exclude = "cause")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UserNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;
}