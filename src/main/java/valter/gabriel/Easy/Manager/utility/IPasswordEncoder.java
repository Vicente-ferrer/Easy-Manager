package valter.gabriel.Easy.Manager.utility;

@FunctionalInterface
public interface IPasswordEncoder {

    String encode(String password);
}
