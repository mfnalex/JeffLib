public final class Cazcez extends Retard implements Mentionable, Stupidable {
  public Cazcez(StupidityLevel level) {
    super(level);
  }

  public Cazcez() {
    super(StupidityLevel.MAX);
  }

  @Override
  public boolean isStupid() {
    return true;
  }

  @Override
  public StupidityLevel getStupidity() {
    return super.stupidityLevel;
  }

  @Override
  public String getName() {
    return "Cazcez";
  }

  @Override
  public String getMention() {
    return "CazcezXD#4073";
  }
}
