package GUI_items;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class LengthFilter extends PlainDocument implements Document {

	private int maxLength;
	private boolean onlyDigits;

	public LengthFilter(int maxLength, boolean onlyDigits) {
		this.maxLength = maxLength;
		this.onlyDigits = onlyDigits;
	}

	@Override
	public void insertString(int offs, String str, AttributeSet a)
			throws BadLocationException {

		if (onlyDigits)
			try {
				int i = Integer.parseInt(str);
				if (i < 0)
					return;
			} catch (NumberFormatException e) {
				return;
			}
		if (offs < maxLength && super.getLength() < maxLength)
			super.insertString(offs, str, a);

	}

}
