package ph.adamw.electrolode.item.core;

public interface IExtendedDescription {
    //Get description - returns a string with delimiters
    // #n = newLine, #s = end of the shortened description
    String getDescription();
}
