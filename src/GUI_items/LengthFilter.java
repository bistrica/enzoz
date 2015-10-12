package GUI_items;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class LengthFilter extends PlainDocument implements Document {

	int maxLength;
	boolean onlyNumbers;

	public LengthFilter(int maxLength, boolean onlyNumbers) {
		this.maxLength = maxLength;
		this.onlyNumbers = onlyNumbers;
	}

	@Override
	public void insertString(int offs, String str, AttributeSet a)
			throws BadLocationException {

		if (onlyNumbers)
			try {
				Integer.parseInt(str);
			} catch (NumberFormatException e) {
				return;
			}
		if (offs < maxLength && super.getLength() < maxLength)
			super.insertString(offs, str, a);

		// super.
	}

}
