package valter.gabriel.Easy.Manager.utility;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class IdLenghtValidationTest {

    private IdLenghtValidation underTest;

    @BeforeEach
    void setup(){
        underTest = new IdLenghtValidation();
    }

    @Test
    @DisplayName("Deve falhar se o valor for diferente de 11 ou 14")
    void checkIfIdLenghtAre11or14(){
        //given
        Long idOne = 12345678912L;
        Long idTwo = 12345678912345L;

        //when
        boolean isIdOneValid = underTest.test(idOne);
        boolean isIdTwoValid = underTest.test(idTwo);

        //then
        assertThat(isIdOneValid).isTrue();
        assertThat(isIdTwoValid).isTrue();
    }

    @Test
    @DisplayName("Deve funcionar se o id tiver tamanho 14")
    void itShouldCheckIfIsCNPJId(){
        Long id = 12345678912345L;

        boolean idValid = underTest.isCNPJId(id);

        assertThat(idValid).isTrue();

    }





}