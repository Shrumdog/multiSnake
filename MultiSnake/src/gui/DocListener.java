package gui;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

public class DocListener implements DocumentListener
{
	private ArrayList<JTextField> IPFields;
	private JPanel IPPanel;
	private Dimension textFieldSize;

	public DocListener(ArrayList<JTextField> IPFs, JPanel IPP)
	{
		super();
		IPFields = IPFs;
		IPPanel = IPP;
		setFieldSize();
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {}

	@Override
	public void removeUpdate(DocumentEvent arg0) {}

	@Override
	public void insertUpdate(DocumentEvent arg0)
	{
		newIPField();
	}

	private void setFieldSize()
	{
		JTextField textField = IPFields.get(0);
		textFieldSize = textField.getSize();
	}

	private void newIPField()
	{
		removeOldListener();

		JTextField textField = new JTextField("");
		textField.setPreferredSize(textFieldSize);
		addTextListener(textField);

		IPFields.add(textField);
		IPPanel.add(textField);
	}

	private void removeOldListener()
	{
		int last = IPFields.size() - 1;
		JTextField end = IPFields.get(last);
		Document doc = end.getDocument();
		doc.removeDocumentListener(this);
	}

	private void addTextListener(JTextField field)
	{
		Document doc = field.getDocument();
		doc.addDocumentListener(new DocListener(IPFields, IPPanel));
	}
}