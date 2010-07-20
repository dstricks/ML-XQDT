/*******************************************************************************
 * Copyright (c) 2009 by SAP AG, Walldorf. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     SAP AG - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.ws.jaxws.dom.runtime.internal.impl;


import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jst.ws.jaxws.dom.runtime.api.DomPackage;
import org.eclipse.jst.ws.jaxws.dom.runtime.api.IServiceEndpointInterface;
import org.eclipse.jst.ws.jaxws.dom.runtime.api.IWebService;
import org.eclipse.jst.ws.jaxws.dom.runtime.api.IWebServiceProject;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IWeb Service Project</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.jst.ws.jaxws.dom.runtime.internal.impl.IWebServiceProjectImpl#getWebServices <em>Web Services</em>}</li>
 *   <li>{@link org.eclipse.jst.ws.jaxws.dom.runtime.internal.impl.IWebServiceProjectImpl#getServiceEndpointInterfaces <em>Service Endpoint Interfaces</em>}</li>
 *   <li>{@link org.eclipse.jst.ws.jaxws.dom.runtime.internal.impl.IWebServiceProjectImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class IWebServiceProjectImpl extends EObjectImpl implements IWebServiceProject {
	/**
	 * The cached value of the '{@link #getWebServices() <em>Web Services</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWebServices()
	 * @generated
	 * @ordered
	 */
	protected EList<IWebService> webServices;

	/**
	 * The cached value of the '{@link #getServiceEndpointInterfaces() <em>Service Endpoint Interfaces</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getServiceEndpointInterfaces()
	 * @generated
	 * @ordered
	 */
	protected EList<IServiceEndpointInterface> serviceEndpointInterfaces;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IWebServiceProjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DomPackage.Literals.IWEB_SERVICE_PROJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IWebService> getWebServices() {
		if (webServices == null)
		{
			webServices = new EObjectContainmentEList<IWebService>(IWebService.class, this, DomPackage.IWEB_SERVICE_PROJECT__WEB_SERVICES);
		}
		return webServices;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IServiceEndpointInterface> getServiceEndpointInterfaces() {
		if (serviceEndpointInterfaces == null)
		{
			serviceEndpointInterfaces = new EObjectContainmentEList<IServiceEndpointInterface>(IServiceEndpointInterface.class, this, DomPackage.IWEB_SERVICE_PROJECT__SERVICE_ENDPOINT_INTERFACES);
		}
		return serviceEndpointInterfaces;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomPackage.IWEB_SERVICE_PROJECT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case DomPackage.IWEB_SERVICE_PROJECT__WEB_SERVICES:
				return ((InternalEList<?>)getWebServices()).basicRemove(otherEnd, msgs);
			case DomPackage.IWEB_SERVICE_PROJECT__SERVICE_ENDPOINT_INTERFACES:
				return ((InternalEList<?>)getServiceEndpointInterfaces()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case DomPackage.IWEB_SERVICE_PROJECT__WEB_SERVICES:
				return getWebServices();
			case DomPackage.IWEB_SERVICE_PROJECT__SERVICE_ENDPOINT_INTERFACES:
				return getServiceEndpointInterfaces();
			case DomPackage.IWEB_SERVICE_PROJECT__NAME:
				return getName();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID)
		{
			case DomPackage.IWEB_SERVICE_PROJECT__WEB_SERVICES:
				getWebServices().clear();
				getWebServices().addAll((Collection<? extends IWebService>)newValue);
				return;
			case DomPackage.IWEB_SERVICE_PROJECT__SERVICE_ENDPOINT_INTERFACES:
				getServiceEndpointInterfaces().clear();
				getServiceEndpointInterfaces().addAll((Collection<? extends IServiceEndpointInterface>)newValue);
				return;
			case DomPackage.IWEB_SERVICE_PROJECT__NAME:
				setName((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID)
		{
			case DomPackage.IWEB_SERVICE_PROJECT__WEB_SERVICES:
				getWebServices().clear();
				return;
			case DomPackage.IWEB_SERVICE_PROJECT__SERVICE_ENDPOINT_INTERFACES:
				getServiceEndpointInterfaces().clear();
				return;
			case DomPackage.IWEB_SERVICE_PROJECT__NAME:
				setName(NAME_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID)
		{
			case DomPackage.IWEB_SERVICE_PROJECT__WEB_SERVICES:
				return webServices != null && !webServices.isEmpty();
			case DomPackage.IWEB_SERVICE_PROJECT__SERVICE_ENDPOINT_INTERFACES:
				return serviceEndpointInterfaces != null && !serviceEndpointInterfaces.isEmpty();
			case DomPackage.IWEB_SERVICE_PROJECT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: "); //$NON-NLS-1$
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //IWebServiceProjectImpl
