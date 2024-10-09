/*
MIT License

Copyright (c) 2021 Universidad de los Andes - ISIS2603

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package co.edu.uniandes.dse.DogSpa.exceptions;

public final class ErrorMessage {
	public static final String RESERVA_NOT_FOUND = "La reserva con el Id dado no fue encontrada";
	public static final String SERVICIO_NOT_FOUND = "El servicio con el Id dado no fue encontrado";
	public static final String SEDE_NOT_FOUND = "La sede con el Id dado no fue encontrada";
	public static final String PRIZE_NOT_FOUND = "The prize with the given id was not found";
	public static final String PAQUETE_NOT_FOUND = "El paquete con el Id dado no fue encontrado";
	public static final String ORGANIZATION_NOT_FOUND = "The organization with the given id was not found";
    public static final String ARTICULO_NOT_FOUND = "El artículo con el id dado no fue encontrado";

	public static final String SERVICIO_NAME_REQUIRED = "The service name is required";
	public static final String SERVICIO_PRICE_REQUIRED = "The service price is required";
	public static final String SERVICIO_DESCRIPCION_REQUIRED = "The service description is required";
	public static final String SERVICIO_IMAGE_REQUIRED = "The service image is required";

	public static final String RESTRICCION_NOT_FOUND = "La restricción con el Id dado no fue encontrada";

	private ErrorMessage() {
		throw new IllegalStateException("Utility class");
	}
}